package club.awesome.api.service

import club.awesome.api.domain.Member
import club.awesome.api.domain.PasswordReset
import club.awesome.api.domain.PasswordResetStatus
import club.awesome.api.dto.AuthRequest
import club.awesome.api.repo.MemberRepo
import club.awesome.api.repo.PasswordResetRepo
import club.awesome.api.resource.exception.ValidationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService(
  private val passwordEncoder: PasswordEncoder,
  private val passwordResetRepo: PasswordResetRepo,
  private val memberRepo: MemberRepo,
  private val utils: Utils,
  private val validators: Validators,
  private val dates: Dates
) {

  fun expireActivePasswordReset(member: Member) {
    val actives = passwordResetRepo.findActiveByMember(member)
    actives.forEach { it.status = PasswordResetStatus.EXPIRED }
    passwordResetRepo.saveAll(actives)
  }

  fun createPasswordReset(member: Member): PasswordReset {
    expireActivePasswordReset(member)
    return passwordResetRepo.save(PasswordReset(member, utils.uniqueId()))
  }

  fun expirePasswordReset(reset: PasswordReset) {
    reset.status = PasswordResetStatus.EXPIRED
    passwordResetRepo.save(reset)
  }

  fun updatePassword(
    member: Member,
    newPass: String,
    confirmPass: String,
    currentPass: String,
    validateCurrent: Boolean = true
  ) {
    if (!validators.isValidPassword(newPass)) throw ValidationException("pass.invalid")
    if (newPass != confirmPass) throw ValidationException("pass.invalid.match")
    if (validateCurrent) {
      if (!passwordEncoder.matches(
          currentPass,
          member.password
        )
      ) throw ValidationException("pass.invalid.current.match")
    }
    member.password = passwordEncoder.encode(newPass)
    memberRepo.save(member)
  }

  fun updateLastLogin(member: Member) {
    member.lastLogin = dates.now()
    memberRepo.save(member)
  }

  fun create(dto: AuthRequest): Member {
    val hashed = passwordEncoder.encode(dto.password)
    val member = Member(
      id = UUID.randomUUID().toString(),
      email = dto.email,
      isAuthor = dto.isAuthor ?: false,
      password = hashed
    )
    return memberRepo.save(member)
  }
}
