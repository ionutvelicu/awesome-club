package club.awesome.api.config.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtConfig {
  @Value("\${security.jwt.header}")
  val header: String = ""

  @Value("\${security.jwt.prefix}")
  val prefix: String = ""

  @Value("\${security.jwt.expiration}")
  val expiration: Int = 0

  @Value("\${security.jwt.secret}")
  val secret: String = ""
}
