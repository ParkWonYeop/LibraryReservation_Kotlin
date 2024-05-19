package com.example.libraryReservationKotlin.fixture

import com.example.libraryReservationKotlin.auth.dto.LoginDto
import com.example.libraryReservationKotlin.auth.dto.RefreshDto
import com.example.libraryReservationKotlin.auth.dto.SignupDto

class AuthFixtures {
    fun loginAddressOne() = LoginDto("01099716733", "1234567")
    fun loginAddressTwo(): LoginDto = LoginDto("01099716737", "1234567")
    fun loginAddressPhoneNumber(phoneNumber: String?): LoginDto = LoginDto(phoneNumber!!, "1234567")
    fun loginAddressPassword(password: String?): LoginDto = LoginDto("01099716737", password!!)
    fun signupAddress(): SignupDto = SignupDto("01099716734", "1234567", "박원엽")
    fun signupAddressPhoneNumber(phoneNumber: String): SignupDto = SignupDto(phoneNumber, "1234567", "박원엽")
    fun signupAddressPassword(password: String): SignupDto = SignupDto("01099716735", password, "박원엽")
    fun signupAddressName(name: String): SignupDto = SignupDto("01099716733", "12345673", name)
    fun refreshToken(refreshToken: String): RefreshDto = RefreshDto("01099716733", refreshToken)
}
