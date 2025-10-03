package club.awesome.api.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path

@Service
class S3Service(
    private val s3: S3Client,
    @Value("\${aws.s3.bucket-name}") private val bucketName: String
) {

    fun uploadFile(path: Path, key: String, contentType: String?) {
        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()

        s3.putObject(request, RequestBody.fromFile(path))
    }

    fun deleteAsset(key: String) {
        val request = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()
        s3.deleteObject(request)
    }
}
