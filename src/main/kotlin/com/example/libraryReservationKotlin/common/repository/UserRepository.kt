package com.example.libraryReservationKotlin.common.repository

import com.example.libraryReservationKotlin.common.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
}
