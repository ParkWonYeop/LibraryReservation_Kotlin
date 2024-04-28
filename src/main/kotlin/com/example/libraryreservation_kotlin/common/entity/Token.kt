package com.example.libraryreservation_kotlin.common.entity

import com.example.libraryreservation_kotlin.common.enum.RoomEnum
import jakarta.persistence.*

@Entity(name = "user_token")
class Token() {
    constructor(user: User,accessToken: String, refreshToken: String) : this() {
        this.user = user
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var tokenId: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @Column(name = "access_token")
    lateinit var accessToken: String

    @Column(name = "refresh_token")
    lateinit var refreshToken: String
}