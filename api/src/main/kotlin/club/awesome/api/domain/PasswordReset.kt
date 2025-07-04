package club.awesome.api.domain

import java.util.*
import jakarta.persistence.*

@Entity
data class PasswordReset (
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="member_id")
  var member: Member = Member(),

  var tag: String = "",

  var status: PasswordResetStatus = PasswordResetStatus.ACTIVE,

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  var createdDate: Date = Date(),

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0
) {
  override fun equals(other: Any?) = other is PasswordReset && other.id == id
  override fun hashCode() = id.hashCode()
  override fun toString() = id.toString()
}

enum class PasswordResetStatus {
  ACTIVE, EXPIRED
}
