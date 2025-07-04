package club.awesome.api.domain

import jakarta.persistence.Entity
import java.util.*
import jakarta.persistence.*

@Entity
data class AuthToken (
  var email: String = "",
  var token: String = "",
  var createdDate: Date = Date(),
  var expirationDate: Date = Date(),
  var valid: Boolean = true,
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0
) {
  override fun hashCode() = id.hashCode()
  override fun toString() = id.toString()
  override fun equals(other: Any?): Boolean {
    return other is AuthToken && other.id == this.id
  }
}
