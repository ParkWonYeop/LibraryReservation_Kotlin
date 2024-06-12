package com.example.libraryReservationKotlin.common.entity

import com.example.libraryReservationKotlin.common.entity.baseEntity.BaseEntity
import com.example.libraryReservationKotlin.common.enum.PermissionEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    var permission: PermissionEnum = PermissionEnum.USER,
) : BaseEntity() {
    constructor(
        phoneNumber: String,
        password: String,
        name: String,
    ) : this(
        id = null,
        phoneNumber = phoneNumber,
        password = password,
        name = name,
    )
}
