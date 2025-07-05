package club.awesome.api.domain

import jakarta.persistence.*
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

  @ManyToOne
  @JoinColumn(name = "product_id")
  var product: Product = Product(),

  @Column(columnDefinition = "TIMESTAMP(4)")
  @Temporal(TemporalType.TIMESTAMP)
  var purchaseDate: Date = Date(),
) : BaseEntity() {
  override fun equals(other: Any?) = other is Member && other.id == id
  override fun hashCode() = id.hashCode()
  override fun toString() = id
}
