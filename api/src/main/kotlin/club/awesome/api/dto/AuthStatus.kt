package club.awesome.api.dto

data class AuthStatus (
  val loggedIn: Boolean = false,
  val label: String = "",
  val name: String = "",
  val isAuthor: Boolean = false,
)
