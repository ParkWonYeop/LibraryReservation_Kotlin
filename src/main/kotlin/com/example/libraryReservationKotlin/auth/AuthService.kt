package com.example.libraryReservationKotlin.auth

import com.example.libraryReservationKotlin.auth.dto.LoginDto
import com.example.libraryReservationKotlin.auth.dto.LoginResponseDto
import com.example.libraryReservationKotlin.auth.dto.RefreshDto
import com.example.libraryReservationKotlin.auth.dto.RefreshResponseDto
import com.example.libraryReservationKotlin.auth.dto.SignupDto
import com.example.libraryReservationKotlin.common.entity.TokenEntity
import com.example.libraryReservationKotlin.common.entity.UserEntity
import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.jwt.JwtUtil
import com.example.libraryReservationKotlin.common.repository.TokenRepository
import com.example.libraryReservationKotlin.common.repository.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val encoder: BCryptPasswordEncoder,
    private val tokenRepository: TokenRepository,
    @Value("\${secret_key}")
    private val secretKey: String,
) {
    private val jwtUtil = JwtUtil()
    private val log = LoggerFactory.getLogger(AuthService::class.java)

    @Transactional
    fun login(
        loginDto: LoginDto,
    ): LoginResponseDto {
        val userEntity = userRepository.findByPhoneNumber(loginDto.phoneNumber)
            ?: throw AccessDeniedException("전화번호가 일치하지 않습니다.")

        if (!encoder.matches(loginDto.password, userEntity.password)) {
            throw AccessDeniedException("비밀번호가 일치하지 않습니다.")
        }

        val accessToken = jwtUtil.generateToken(userEntity, secretKey)
        val refreshToken = jwtUtil.createRefreshToken(secretKey)

        val tokenEntity = tokenRepository.findByUser(userEntity)?.apply {
            this.accessToken = accessToken
            this.refreshToken = refreshToken
        }
            ?: TokenEntity(userEntity, accessToken, refreshToken)

        tokenRepository.save(tokenEntity)

        log.info("login : success - " + userEntity.phoneNumber)

        return LoginResponseDto(refreshToken, accessToken)
    }

    @Transactional
    fun signup(
        signupDto: SignupDto,
    ) {
        val phoneNumber = signupDto.phoneNumber

        userRepository.findByPhoneNumber(phoneNumber)?.let {
            throw CustomException(CommunalResponse.ALREADY_SIGNUP_PHONENUMBER)
        }

        val passwordEncode = encoder.encode(signupDto.password)

        val userEntity = UserEntity(phoneNumber, passwordEncode, signupDto.name)

        userRepository.save(userEntity)
    }

    @Transactional
    fun refreshToken(
        refreshDto: RefreshDto,
    ): RefreshResponseDto {
        if (jwtUtil.isExpired(refreshDto.refreshToken, secretKey)) {
            throw AccessDeniedException("refreshToken is expired")
        }

        val phoneNumber = refreshDto.phoneNumber

        val userEntity = userRepository.findByPhoneNumber(phoneNumber)
            ?: throw AccessDeniedException("user not found")

        val tokenEntity = tokenRepository.findByUser(userEntity)
            ?: throw AccessDeniedException("token not found")

        if (tokenEntity.refreshToken != refreshDto.refreshToken) {
            throw AccessDeniedException("refreshToken is not correct")
        }

        val accessToken = jwtUtil.generateToken(userEntity, secretKey)
        val refreshToken = jwtUtil.createRefreshToken(secretKey)

        tokenEntity.apply {
            this.accessToken = accessToken
            this.refreshToken = refreshToken
        }

        tokenRepository.save(tokenEntity)

        return RefreshResponseDto(refreshToken, accessToken)
    }
}
