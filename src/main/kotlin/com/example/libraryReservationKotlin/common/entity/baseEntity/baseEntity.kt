package com.example.libraryReservationKotlin.common.entity.baseEntity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
class baseEntity(
    @CreationTimestamp
    @Column(updatable = false)
    val createAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updateAt: LocalDateTime = LocalDateTime.now(),

    var deleteAt: LocalDateTime? = null,
) {
    fun softDelete() {
        this.deleteAt = LocalDateTime.now()
    }
}
