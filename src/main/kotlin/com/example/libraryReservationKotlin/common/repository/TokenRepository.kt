package com.example.libraryReservationKotlin.common.repository

import com.example.libraryReservationKotlin.common.entity.TokenEntity
import com.example.libraryReservationKotlin.common.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<TokenEntity, Long> {
    fun findByUser(user: UserEntity): TokenEntity?
}
