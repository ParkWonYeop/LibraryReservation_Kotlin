package com.example.libraryreservation_kotlin.common.repository

import com.example.libraryreservation_kotlin.common.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
}