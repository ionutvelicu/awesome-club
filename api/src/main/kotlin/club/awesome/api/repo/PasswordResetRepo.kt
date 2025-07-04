package club.awesome.api.repo

import club.awesome.api.domain.Member
import club.awesome.api.domain.PasswordReset
import club.awesome.api.domain.PasswordResetStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PasswordResetRepo : JpaRepository<PasswordReset, Long> {
  @Query("select pr from PasswordReset pr where pr.member = :member and pr.status = 0")
  fun findActiveByMember(member: Member): List<PasswordReset>

  fun findOneByTagAndStatus(tag: String, status: PasswordResetStatus = PasswordResetStatus.ACTIVE): PasswordReset?

  @Query("select pr from PasswordReset pr join fetch pr.member mem where pr.tag = :tag and pr.status = 0")
  fun findOneByTag(tag: String): PasswordReset?
}
