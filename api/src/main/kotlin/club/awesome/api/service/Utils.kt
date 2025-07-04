package club.awesome.api.service
import club.awesome.api.domain.Member
import club.awesome.api.dto.AuthUser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class Utils {
  private val regexAlphanumeric = Regex("[^A-Za-z0-9 -]")

  private val log: Logger = LoggerFactory.getLogger(Utils::class.java)
  private val mapper = jacksonObjectMapper()

  fun toJson(obj: Any): String {
    return try {
      mapper.writeValueAsString(obj)
    } catch (e: JsonProcessingException) {
      log.error(e.localizedMessage, e)
      ""
    }
  }

  fun toUrl(value: String): String {
    return value
      .replace(regexAlphanumeric, "")
      .trim()
      .replace("\\s+".toRegex(), "-")
  }

  fun isAuthenticated(): Boolean {
    val context = SecurityContextHolder.getContext()
    val auth = context.authentication
    return (auth is UsernamePasswordAuthenticationToken)
  }

  fun loggedEmail(): String? {
    val context = SecurityContextHolder.getContext()
    val auth = context.authentication
    return if (auth is UsernamePasswordAuthenticationToken) auth.name else null
  }

  fun loggedId(): String? {
    val context = SecurityContextHolder.getContext()
    val auth = context.authentication

    return if (auth is UsernamePasswordAuthenticationToken) {
      val user = auth.principal as AuthUser
      return user.memberId
    } else {
      null
    }
  }

  fun uniqueId(count: Int = 12): String = RandomStringUtils.random(count, true, true)

  fun getAuthLabel(member: Member): String {
    member.name.trim().takeIf { it.isNotEmpty() }?.let { name ->
      return name.split("\\s+".toRegex())
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString("")
    }

    member.email.trim().takeIf { it.isNotEmpty() }?.let { email ->
      val username = email.substringBefore("@")
      val parts = username.split(".", "_", "-")

      return when {
        parts.size >= 2 -> parts[0].firstOrNull()?.uppercaseChar()?.toString() + (parts[1].firstOrNull()?.uppercaseChar() ?: "")
        else -> username.take(2).uppercase()
      }
    }

    return ""
  }

}
