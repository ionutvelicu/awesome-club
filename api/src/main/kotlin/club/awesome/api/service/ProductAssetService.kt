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

        val tmpFile = Files.createTempFile("upload-", file.originalFilename).toFile()
        file.transferTo(tmpFile)

        val originalKey = "product/$productId/section-$sectionId/${asset.id}/original-${file.originalFilename}"
        s3Service.uploadFile(tmpFile.toPath(), originalKey, file.contentType)
        asset.originalKey = originalKey
        assetRepo.save(asset)

        // Implement transcoding to HLS
        val hlsKey = "product/$productId/section-$sectionId/${asset.id}/hls/index.m3u8"
        // here launch an asynchronous process that does ffmpeg HLS
        // transcoder.transcodeToHLS(tmpFile, hlsKey)
        asset.hlsKey = hlsKey
        assetRepo.save(asset)

        return asset
    }
}
