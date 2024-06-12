package com.example.libraryReservationKotlin.common.entity

import com.example.libraryReservationKotlin.common.entity.baseEntity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "user_token")
class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(name = "access_token")
    var accessToken: String,

    @Column(name = "refresh_token")
    var refreshToken: String,
) : BaseEntity() {
    constructor(
        user: UserEntity,
        accessToken: String,
        refreshToken: String,
    ) : this(
        id = null,
        user = user,
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}
