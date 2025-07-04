package club.awesome.api.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.util.*

@MappedSuperclass
abstract class BaseEntity {

  @Column(columnDefinition = "TIMESTAMP(4)", nullable = false, updatable = false)
  var createdDate: Date = Date()

  @Column(columnDefinition = "TIMESTAMP(4)", nullable = false)
  var updatedDate: Date = Date()

  @Column(columnDefinition = "TIMESTAMP(4)", nullable = false)
  var removedDate: Date = Date()

  var removed: Boolean = false

  @PrePersist
  fun onCreate() {
    val now = Date()
    createdDate = now
    updatedDate = now
  }

  @PreUpdate
  fun onUpdate() {
    updatedDate = Date()
  }
}
