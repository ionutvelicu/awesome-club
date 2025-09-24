package club.awesome.api.domain

import club.awesome.api.dto.ProductAssetDto
import jakarta.persistence.*

@Entity
@Table(name = "product_assets")
data class ProductAsset(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val productId: String,

    val section: String,

    var mime: String? = null,

    var size: Long = 0,

    var status: String? = null,

    var originalKey: String? = null,

    var hlsKey: String? = null
): BaseEntity() {
    fun toDto(): ProductAssetDto {
        return ProductAssetDto(
            id = this.id,
            section = this.section,
            mime = this.mime,
            size = this.size,
            status = this.status,
            originalKey = this.originalKey,
            hlsKey = this.hlsKey
        )
    }
}
