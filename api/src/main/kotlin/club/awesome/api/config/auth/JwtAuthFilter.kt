package club.awesome.api.config.auth

import club.awesome.api.service.AuthService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthorizationFilter(
  private val userDetailsService: UserDetailsService,
  private val authService: AuthService,
  private val config: JwtConfig
) : OncePerRequestFilter() {
  private val log = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    log.info("➡️ filter req: ${request.method} ${request.requestURI}")

    if (request.requestURL.contains("locales")) {
      chain.doFilter(request, response)
      return
    }
    val authentication = getAuthenticated(request)
    if (authentication == null) {
      log.info("➡️ auth is null")
      chain.doFilter(request, response)
      return
    }
    SecurityContextHolder.getContext().authentication = authentication
    chain.doFilter(request, response)
  }

  private fun getAuthenticated(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
    val header = request.getHeader(config.header)
    if (header == null || !header.startsWith(config.prefix)) return null
    val token = header.replace(config.prefix, "").trim()

    return try {
      val claims = Jwts.parser()
        .setSigningKey(config.secret.toByteArray())
        .build()
        .parseClaimsJws(token)
        .body

      val email = claims.subject
      val authorities = getAuthorities(claims)

      if (email.isNotEmpty() && authService.isValid(token)) {
        val principal = userDetailsService.loadUserByUsername(email)
        UsernamePasswordAuthenticationToken(principal, null, authorities)
      } else {
        null
      }
    } catch (ex: Exception) {
      null
    }
  }

  private fun getAuthorities(claims: Claims): List<SimpleGrantedAuthority> {
    val authorities = mutableListOf<SimpleGrantedAuthority>()

    val claimAuthorities = claims["authorities"]
    if (claimAuthorities is List<*>) {
      authorities.addAll(claimAuthorities.map {
        SimpleGrantedAuthority(it as String)
      })
    }

    return authorities
  }
}
