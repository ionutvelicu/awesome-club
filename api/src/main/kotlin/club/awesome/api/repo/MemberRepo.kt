package club.awesome.api.repo

import club.awesome.api.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberRepo: JpaRepository<Member, String> {
  fun findOneByEmail(email: String): Member?
  fun findOneById(id: String): Member?

  @Query("select mem from Member mem where mem.email like :key")
  fun searchByEmail(key: String): List<Member>
}
