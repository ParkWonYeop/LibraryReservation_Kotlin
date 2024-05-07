package com.example.libraryreservation_kotlin.common.repository

import com.example.libraryreservation_kotlin.common.entity.TokenEntity
import com.example.libraryreservation_kotlin.common.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository: JpaRepository<TokenEntity, Long> {
    fun findByUser(user: UserEntity): TokenEntity?
}