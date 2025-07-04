package club.awesome.api.config.auth

import club.awesome.api.service.Utils
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthenticationEntryPoint(
  val utils: Utils
) : AuthenticationEntryPoint {

  @Throws(IOException::class, ServletException::class)
  override fun commence(httpServletRequest: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
    println("AuthEntryPoint triggered: ${e::class.simpleName} - ${e.message}")

    response.status = HttpServletResponse.SC_UNAUTHORIZED
    response.contentType = "application/json"
    response.characterEncoding = "UTF-8"
    response.writer.write(utils.toJson(ErrorResponse(e.message, HttpServletResponse.SC_UNAUTHORIZED)))
  }
}

class ErrorResponse(val message: String?, val status: Int)
