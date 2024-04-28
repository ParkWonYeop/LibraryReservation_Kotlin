package com.example.libraryreservation_kotlin.common.repository

import com.example.libraryreservation_kotlin.common.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findUserEntityByPhoneNumber(phoneNumber: String): User?
}