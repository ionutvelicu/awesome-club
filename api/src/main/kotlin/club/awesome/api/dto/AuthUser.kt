package club.awesome.api.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthUser(
  username: String,
  password: String,
  enabled: Boolean,
  accountNonExpired: Boolean,
  credentialsNonExpired: Boolean,
  accountNonLocked: Boolean,
  authorities: Collection<GrantedAuthority>,
  val memberId: String,
  val firstName: String
) : User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
