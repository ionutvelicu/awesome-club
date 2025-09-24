package club.awesome.api.dto

data class ProductAssetDto(
    val id: Long,
    val section: String,
    val mime: String?,
    val size: Long,
    val status: String?,
    val originalKey: String?,
    val hlsKey: String?
)
