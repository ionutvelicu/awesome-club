package club.awesome.api.service

import club.awesome.api.config.auth.JwtConfig
import org.springframework.security.authentication.AuthenticationManager
import club.awesome.api.domain.AuthToken
import club.awesome.api.repo.AuthenticationTokenRepo
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class AuthService(
  val config: JwtConfig,
  private val authenticationTokenRepo: AuthenticationTokenRepo,
  private val dates: Dates,
  private val authConfig: AuthenticationConfiguration
) {

  fun login(email: String, password: String): Authentication? {
    return try {
      val token = UsernamePasswordAuthenticationToken(email, password)
      val auth = authConfig.authenticationManager.authenticate(token)
      auth
    } catch (e: Exception) {
      null
    }
  }

  fun isValid(token: String): Boolean {
    val auth = authenticationTokenRepo.findByToken(token)
    return auth == null || auth.valid
  }

  fun getNewToken(email: String, auth: Authentication): String {
    val authorities = auth.authorities.map { it.authority }

    val key = Keys.hmacShaKeyFor(config.secret.toByteArray())

    return Jwts.builder()
      .subject(email)
      .claim("authorities", authorities)
      .issuedAt(dates.now())
      .expiration(getTokenExpiration())
      .signWith(key, Jwts.SIG.HS512)
      .compact()
  }

  fun saveToken(email: String, token: String) {
    authenticationTokenRepo.save(AuthToken(email, token, dates.now(), getTokenExpiration()))
  }

  fun invalidateTokens(email: String) {
    val tokens = authenticationTokenRepo.findValidByEmail(email)
    tokens.forEach { it.valid = false }
    authenticationTokenRepo.saveAll(tokens)
  }

  private fun getTokenExpiration(): Date {
    return Date(System.currentTimeMillis() + config.expiration * 1000)
  }
}
