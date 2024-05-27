package com.example.libraryReservationKotlin.fixture

import com.example.libraryReservationKotlin.auth.dto.LoginDto
import com.example.libraryReservationKotlin.auth.dto.RefreshDto
import com.example.libraryReservationKotlin.auth.dto.SignupDto

class AuthFixtures {
    fun loginAddressTwo(): LoginDto = LoginDto("01099716737", "1234567")
    fun loginAddressPhoneNumber(phoneNumber: String?): LoginDto = LoginDto(phoneNumber!!, "1234567")
    fun loginAddressPassword(password: String?): LoginDto = LoginDto("01099716737", password!!)
    fun signupAddress(): SignupDto = SignupDto("01099716734", "1234567", "박원엽")
    fun signupAddressPhoneNumber(phoneNumber: String): SignupDto = SignupDto(phoneNumber, "1234567", "박원엽")
    fun signupAddressPassword(password: String): SignupDto = SignupDto("01099716735", password, "박원엽")
    fun signupAddressName(name: String): SignupDto = SignupDto("01099716733", "12345673", name)
    fun refreshTokenAddress(refreshToken: String): RefreshDto = RefreshDto("01099716733", refreshToken)
    fun accessTokenOne() = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIwMTA5OTcxNjczMyIsImlhdCI6MTcxNjcyMjQ1MywiZXhwIjo0ODcyMzk2MDUzfQ.TpYFhStRMtDXP750Psdwm0r5sUuIxcFUg6DbxgtAnTzxG31CzP_1dlRFVeU3SQhW"
    fun refreshTokenOne() = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTY3MjI0NTMsImV4cCI6NDg3MjM5NjA1M30.97UZ03ZFcWFZ4B9upvsthB7awm4Xs7VkXYamG1KN6KW14_f4wuX2mKESJTpENZ8m"
    fun accessTokenTwo() = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIwMTA5OTcxNjczNyIsImlhdCI6MTcxNjcxOTI1NiwiZXhwIjo0ODcyMzkyODU2fQ.ScGLC0K8YBf_gOBTukV2ak0-Tv69k77s-biiBg1zGkJifY2cFuTKdYu9QAnsfYMq"
}
