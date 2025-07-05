package club.awesome.api.domain

import club.awesome.api.dto.MemberProductDto
import club.awesome.api.dto.MemberProductLightDto
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.springframework.data.jpa.repository.Temporal
import java.util.*

@Entity
data class MemberProduct(
  @Id
  @Column(columnDefinition = "varchar(36)")
  var id: String = "",

  var ownerId: String = "",

  var price: Double = 0.0,

  var paymentData: String = "",

  var progress: Double = 0.0,

  var complete: Boolean = false,

  @ManyToOne
  @JoinColumn(name = "product_id")
  var product: Product = Product(),

  @Column(columnDefinition = "TIMESTAMP(4)")
  @Temporal(TemporalType.TIMESTAMP)
  var purchaseDate: Date = Date(),
) : BaseEntity() {
  override fun equals(other: Any?) = other is MemberProduct && other.id == id
  override fun hashCode() = id.hashCode()
  override fun toString() = id

  fun toDto(): MemberProductDto {
    val dto = MemberProductDto(
      purchaseId = this.id,
      complete = this.complete,
      progress = this.progress
    )
    if (Hibernate.isInitialized(this.product)) {
      dto.product = this.product.toDto()
    }
    return dto
  }

  fun toLightDto(): MemberProductLightDto {
    val dto = MemberProductLightDto(
      purchaseId = this.id,
      complete = this.complete,
      progress = this.progress
    )
    if (Hibernate.isInitialized(this.product)) {
      dto.name = this.product.name
    }
    return dto
  }
}
