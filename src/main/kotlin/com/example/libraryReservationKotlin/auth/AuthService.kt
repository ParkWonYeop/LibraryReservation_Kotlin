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
    fun login(loginDto: LoginDto): LoginResponseDto {
        val userEntity: UserEntity = userRepository.findByPhoneNumber(loginDto.phoneNumber)
            ?: throw AccessDeniedException("전화번호가 일치하지 않습니다.")

        if (!encoder.matches(loginDto.password, userEntity.password)) {
            throw AccessDeniedException("비밀번호가 일치하지 않습니다.")
        }

        val accessToken: String = jwtUtil.generateToken(userEntity, secretKey)
        val refreshToken: String = jwtUtil.createRefreshToken(secretKey)

        val tokenEntity: TokenEntity =
            tokenRepository.findByUser(userEntity)
                ?: TokenEntity(null, userEntity, accessToken, refreshToken)

        tokenEntity.user = userEntity
        tokenEntity.refreshToken = refreshToken
        tokenEntity.accessToken = accessToken

        tokenRepository.save(tokenEntity)

        log.info("login : success - " + userEntity.phoneNumber)

        return LoginResponseDto(refreshToken, accessToken)
    }

    @Transactional
    fun signup(signupDto: SignupDto) {
        val phoneNumber: String = signupDto.phoneNumber
        if (userRepository.findByPhoneNumber(phoneNumber) != null) {
            throw CustomException(CommunalResponse.ALREADY_SIGNUP_PHONENUMBER)
        }

        val passwordEncode = encoder.encode(signupDto.password)

        val userEntity = UserEntity(null, phoneNumber, passwordEncode, signupDto.name)

        userRepository.save(userEntity)
    }

    @Transactional
    fun refreshToken(refreshDto: RefreshDto): RefreshResponseDto {
        if (jwtUtil.isExpired(refreshDto.refreshToken, secretKey)) {
            throw AccessDeniedException("refreshToken is expired")
        }

        val phoneNumber: String = refreshDto.phoneNumber

        val userEntity: UserEntity = userRepository.findByPhoneNumber(phoneNumber)
            ?: throw AccessDeniedException("user not found")

        val tokenEntity: TokenEntity = tokenRepository.findByUser(userEntity)
            ?: throw AccessDeniedException("token not found")

        if (tokenEntity.refreshToken != refreshDto.refreshToken) {
            throw AccessDeniedException("refreshToken is not correct")
        }

        val accessToken: String = jwtUtil.generateToken(userEntity, secretKey)
        val refreshToken: String = jwtUtil.createRefreshToken(secretKey)

        tokenEntity.accessToken = accessToken
        tokenEntity.refreshToken = refreshToken

        tokenRepository.save(tokenEntity)

        return RefreshResponseDto(refreshToken, accessToken)
    }
}
