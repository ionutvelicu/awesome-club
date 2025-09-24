package club.awesome.api.service

import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path

@Service
class S3Service {

    private val bucketName = "my-awesome-bucket"
    private val s3: S3Client = S3Client.builder()
        .region(Region.US_EAST_1) // set region as needed
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()

    /**
     * Upload file to S3
     * @param path - local path to the file
     * @param key - S3 key (e.g., "product/123/section-1/original.mp4")
     * @param contentType - mime type
     */
    fun uploadFile(path: Path, key: String, contentType: String?) {
        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()

        s3.putObject(request, path)
    }
}
