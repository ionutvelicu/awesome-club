package club.awesome.api.service

import club.awesome.api.domain.ProductAsset
import club.awesome.api.repo.ProductAssetRepo
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files

@Service
class ProductAssetService(
    private val s3Service: S3Service,
    private val assetRepo: ProductAssetRepo
) {

    fun uploadAsset(productId: String, sectionId: String, file: MultipartFile): ProductAsset {
        val asset = ProductAsset(
            productId = productId,
            section = sectionId,
            mime = file.contentType,
            size = file.size,
            status = "UPLOADED"
        )
        assetRepo.save(asset)

        // 1. Upload original on S3
        val originalKey = "product/$productId/section-$sectionId/${asset.id}/original-${file.originalFilename}"
        s3Service.uploadMultipart(file, originalKey, file.contentType)
        asset.originalKey = originalKey
        assetRepo.save(asset)

        // 2. set HLS key
        val hlsKey = "product/$productId/section-$sectionId/${asset.id}/hls/index.m3u8"
        asset.hlsKey = hlsKey
        asset.status = "PROCESSING"
        assetRepo.save(asset)

        // 3. start transcoding
        transcodeToHls(file, asset.id!!, hlsKey)

        return asset
    }

    /**
     * Process video file and transcode to HLS format
     */
    @Async
    fun transcodeToHls(file: MultipartFile, assetId: String, hlsKey: String) {
        val tmpFile = File.createTempFile("video-", ".mp4")
        file.transferTo(tmpFile)

        val outputDir = File.createTempFile("hls-", "")
        outputDir.delete()
        outputDir.mkdirs()

        val ffmpegCmd = listOf(
            "ffmpeg",
            "-i", tmpFile.absolutePath,
            "-profile:v", "baseline",
            "-level", "3.0",
            "-start_number", "0",
            "-hls_time", "10",
            "-hls_list_size", "0",
            "-f", "hls",
            "${outputDir.absolutePath}/index.m3u8"
        )

        ProcessBuilder(ffmpegCmd)
            .redirectErrorStream(true)
            .start()
            .waitFor()

        // upload HLS to S3
        outputDir.listFiles()?.forEach { f ->
            val key = hlsKey.replace("index.m3u8", f.name)
            s3Service.uploadFile(f, key, "application/vnd.apple.mpegurl")
        }

        // update asset is DB as READY
        val asset = assetRepo.findById(assetId).orElseThrow()
        asset.status = "READY"
        assetRepo.save(asset)

        tmpFile.delete()
        outputDir.deleteRecursively()
    }
}
