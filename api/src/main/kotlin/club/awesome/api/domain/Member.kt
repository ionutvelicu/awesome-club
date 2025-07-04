package club.awesome.api.domain

import jakarta.persistence.*
import org.springframework.data.jpa.repository.Temporal
import java.util.*

@Entity
data class Member(
  @Id
  @Column(columnDefinition = "varchar(36)")
  var id: String = "",

  var email: String = "",

  var password: String = "",

  var name: String = "",

  var isAuthor: Boolean = false,

  @Column(columnDefinition = "TIMESTAMP(4)")
  @Temporal(TemporalType.TIMESTAMP)
  var lastLogin: Date = Date(),

  var state: MemberState = MemberState.ACTIVE,

  @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
  var products: MutableList<Product> = mutableListOf()
) : BaseEntity() {
  override fun equals(other: Any?) = other is Member && other.id == id
  override fun hashCode() = id.hashCode()
  override fun toString() = id
}

enum class MemberState {
  ACTIVE, INACTIVE, DELETED, LOCKED;
}
