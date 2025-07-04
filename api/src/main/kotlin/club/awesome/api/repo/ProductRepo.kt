package club.awesome.api.repo

import club.awesome.api.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepo: JpaRepository<Product, String> {
  @Query("SELECT p FROM Product p WHERE p.id = :id and p.removed = false")
  fun findOneById(id: String): Product?

  @Query("SELECT p FROM Product p WHERE p.url = :url and p.removed = false")
  fun findOneByUrl(url: String): Product?

  @Query("SELECT p FROM Product p LEFT JOIN FETCH p.author WHERE p.id = :id")
  fun findOneWithAuthorById(id: String): Product?

  @Query("SELECT p FROM Product p WHERE p.author.id = :memberId AND p.removed = false")
  fun findAllByMemberId(memberId: String): List<Product>
}
