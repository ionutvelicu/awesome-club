package club.awesome.api.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path

@Service
class S3Service(
    @Value("\${aws.access-key}") private val accessKey: String,
    @Value("\${aws.secret-key}") private val secretKey: String,
    @Value("\${aws.region}") private val region: String,
    @Value("\${aws.s3.bucket-name}") private val bucketName: String
) {

    private val s3: S3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
            )
        )
        .build()

    fun uploadFile(path: Path, key: String, contentType: String?) {
        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()

        s3.putObject(request, path)
    }
}
