package com.example.libraryReservationKotlin.common.entity

import com.example.libraryReservationKotlin.common.enum.PermissionEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    var permission: PermissionEnum = PermissionEnum.USER,

    @CreationTimestamp
    var createAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updateAt: LocalDateTime = LocalDateTime.now(),
)
