package club.awesome.api.service

import club.awesome.api.domain.ProductAsset
import club.awesome.api.repo.ProductAssetRepo
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path

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

        // Create temporary file
        val tmpFile: Path = Files.createTempFile("upload-", file.originalFilename)
        file.transferTo(tmpFile.toFile())

        // Upload original to S3
        val originalKey = "product/$productId/section-$sectionId/${asset.id}/original-${file.originalFilename}"
        s3Service.uploadFile(tmpFile, originalKey, file.contentType)
        asset.originalKey = originalKey
        assetRepo.save(asset)

        // Start transcoding
        val hlsKey = "product/$productId/section-$sectionId/${asset.id}/hls/index.m3u8"
        transcodeToHLSAsync(tmpFile, hlsKey, asset)

        return asset
    }

    @Async
    fun transcodeToHLSAsync(inputFile: Path, hlsKey: String, asset: ProductAsset) {
        try {
            // temporary directory for HLS
            val hlsDir: Path = Files.createTempDirectory("hls-${asset.id}")

            // ffmpeg command for HLS
            val process = ProcessBuilder(
                "ffmpeg", "-i", inputFile.toAbsolutePath().toString(),
                "-codec:", "copy",
                "-start_number", "0",
                "-hls_time", "10",
                "-hls_list_size", "0",
                "-f", "hls",
                hlsDir.resolve("index.m3u8").toAbsolutePath().toString()
            ).inheritIO().start()

            val exitCode = process.waitFor()
            if (exitCode != 0) {
                throw RuntimeException("FFmpeg transcoding failed with exit code $exitCode")
            }

            // Upload HLS files to S3
            Files.list(hlsDir).forEach { path ->
                val key = "product/${asset.productId}/section-${asset.section}/${asset.id}/hls/${path.fileName}"
                s3Service.uploadFile(path, key, "video/mp2t")
            }

            asset.hlsKey = hlsKey
            asset.status = "READY"
            assetRepo.save(asset)

        } catch (e: Exception) {
            asset.status = "ERROR"
            assetRepo.save(asset)
            throw e
        }
    }
}
