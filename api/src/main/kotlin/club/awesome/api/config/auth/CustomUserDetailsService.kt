package club.awesome.api.config.auth

import club.awesome.api.domain.Member
import club.awesome.api.domain.MemberState
import club.awesome.api.dto.AuthUser
import club.awesome.api.repo.MemberRepo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("customUserDetailsService")
class CustomUserDetailsService(
  val memberRepo: MemberRepo
) : UserDetailsService {

  @Transactional(readOnly = true)
  @Throws(UsernameNotFoundException::class)
  override fun loadUserByUsername(username: String): UserDetails {
    val member = memberRepo.findOneByEmail(username) ?: throw UsernameNotFoundException("Authentication failed")

    return AuthUser(member.email,
      member.password,
      MemberState.ACTIVE == member.state,
      accountNonExpired = true,
      credentialsNonExpired = true,
      accountNonLocked = true,
      authorities = getGrantedAuthorities(member),
      memberId = member.id,
      firstName = member.email)
  }

  private fun getGrantedAuthorities(member: Member): Set<GrantedAuthority> {
    return  setOf(SimpleGrantedAuthority("ROLE_USER"))
  }
}
