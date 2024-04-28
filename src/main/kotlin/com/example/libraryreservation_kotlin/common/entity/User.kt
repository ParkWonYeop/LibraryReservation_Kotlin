package com.example.libraryreservation_kotlin.common.entity

import com.example.libraryreservation_kotlin.common.enum.PermissionEnum
import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@Entity(name = "users")
class UserEntity() {
    constructor(phoneNumber: String, password: String, name: String) : this() {
        this.phoneNumber = phoneNumber
        this.password = password
        this.name = name
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0

    @Column(name = "phone_number", nullable = false)
    lateinit var phoneNumber: String

    @Column(name = "password", nullable = false)
    lateinit var password: String

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "permission", nullable = false)
    var permission: PermissionEnum = PermissionEnum.USER

    @CreationTimestamp
    var createAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    var updateAt: LocalDateTime = LocalDateTime.now()
}