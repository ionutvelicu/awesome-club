package club.awesome.api.dto

data class AuthRequest(
  val email: String,
  val password: String,
  val isAuthor: Boolean? = false
)
