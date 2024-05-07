package com.example.libraryreservation_kotlin.common.entity

import com.example.libraryreservation_kotlin.common.enum.PermissionEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@Entity
@Table(name = "user")
data class UserEntity(
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
    var updateAt: LocalDateTime = LocalDateTime.now()
)