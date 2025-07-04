package club.awesome.api.domain

import club.awesome.api.dto.ProductDto
import jakarta.persistence.*

@Entity
data class Product(
  @Id
  @Column(columnDefinition = "varchar(36)")
  var id: String = "",

  var name: String = "",

  var url: String = "",

  var description: String = "",

  var hero: String = "",

  @Column(columnDefinition = "text")
  var data: String = "",

  var status: ProductStatus = ProductStatus.DRAFT,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  var author: Member? = null
) : BaseEntity() {
  override fun equals(other: Any?) = other is Member && other.id == id
  override fun hashCode() = id.hashCode()
  override fun toString() = id

  fun toDto() = ProductDto(
    id = id,
    name = name,
    description = description,
    url = url,
    hero = hero,
    status = status.name,
    data = data,
    authorId = author?.id ?: "",
    authorName = author?.name ?: ""
  )
}

enum class ProductStatus {
  DRAFT, PUBLISHED;
}
