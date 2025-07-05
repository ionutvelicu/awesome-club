package club.awesome.api.repo

import club.awesome.api.domain.MemberProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberProductRepo: JpaRepository<MemberProduct, String> {
  @Query("SELECT mp FROM MemberProduct mp LEFT JOIN FETCH mp.product WHERE mp.ownerId = :ownerId and mp.product.id = :productId")
  fun findOneByOwnerIdAndProductIdWidthProduct(ownerId: String, productId: String): MemberProduct?

  @Query("SELECT mp FROM MemberProduct mp LEFT JOIN FETCH mp.product WHERE mp.id = :id")
  fun findOneByIdWithProduct(id: String): MemberProduct?

  @Query("SELECT mp FROM MemberProduct mp LEFT JOIN FETCH mp.product WHERE mp.ownerId = :ownerId")
  fun findByOwnerId(ownerId: String): List<MemberProduct>
}
