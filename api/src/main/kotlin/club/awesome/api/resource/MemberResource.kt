package club.awesome.api.resource

import club.awesome.api.repo.MemberRepo
import club.awesome.api.resource.exception.NotFoundException
import club.awesome.api.service.Utils
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberResource (
  private val utils: Utils,
  private val memberRepo: MemberRepo,
) {
  private val log = LoggerFactory.getLogger(MemberResource::class.java)

  @PostMapping("/members/author")
  fun convertToAuthor() {
    utils.loggedId()?.let { id ->
      val member = memberRepo.findOneById(id) ?: throw NotFoundException("member.not.found")
      member.isAuthor = true
      memberRepo.save(member)
    }
  }
}
