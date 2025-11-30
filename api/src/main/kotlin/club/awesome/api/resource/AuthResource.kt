package club.awesome.api.resource

import club.awesome.api.dto.AuthRequest
import club.awesome.api.dto.AuthResponse
import club.awesome.api.dto.AuthStatus
import club.awesome.api.repo.MemberRepo
import club.awesome.api.resource.exception.NotFoundException
import club.awesome.api.resource.exception.ValidationException
import club.awesome.api.service.AuthService
import club.awesome.api.service.MemberService
import club.awesome.api.service.Utils
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthResource(
  private val utils: Utils,
  private val memberRepo: MemberRepo,
  private val authService: AuthService,
  private val memberService: MemberService
) {
  private val log = LoggerFactory.getLogger(AuthResource::class.java)

  @GetMapping("/public/auth/status")
  fun getStatus(): AuthStatus {
    val id = utils.loggedId() ?: return AuthStatus(loggedIn = false)

    val member = memberRepo.findOneById(id) ?: throw NotFoundException("member.not.found")
    return AuthStatus(
      loggedIn = true,
      label = utils.getAuthLabel(member),
      name = member.name,
      isAuthor = member.isAuthor
    )
  }

  @PostMapping("/public/auth/login")
  fun login(@RequestBody req: AuthRequest): AuthResponse {
    val token = login(req.email, req.password)

    val member = memberRepo.findOneByEmail(req.email) ?: throw ValidationException("email.pass.incorrect")
    memberService.updateLastLogin(member)

    return AuthResponse(member.id, token)
  }

  @PostMapping("/public/auth/register")
  fun register(@RequestBody req: AuthRequest): AuthResponse {
    if (memberRepo.findOneByEmail(req.email) != null) throw ValidationException("email.already.registered")

    val member = memberService.create(req)
    val token = login(req.email, req.password)
    return AuthResponse(member.id, token)
  }

  private fun login(email: String, password: String): String {
    val auth = authService.login(email, password) ?: throw ValidationException("email.pass.incorrect")
    val token = authService.getNewToken(email, auth)
    authService.saveToken(email, token)
    return token
  }
}
