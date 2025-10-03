package club.awesome.api.repo

import club.awesome.api.domain.ProductAsset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductAssetRepo : JpaRepository<ProductAsset, Long> {
    fun findAllByProductId(productId: String): List<ProductAsset>
}
