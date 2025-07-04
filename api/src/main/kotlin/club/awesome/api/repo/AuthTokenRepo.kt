package club.awesome.api.repo

import club.awesome.api.domain.AuthToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AuthenticationTokenRepo : JpaRepository<AuthToken, Long> {
  @Query("from AuthToken tk where tk.email = :email and tk.valid = true")
  fun findValidByEmail(email: String): List<AuthToken>

  @Query("from AuthToken tk where tk.token = :token")
  fun findByToken(token: String): AuthToken?
}
