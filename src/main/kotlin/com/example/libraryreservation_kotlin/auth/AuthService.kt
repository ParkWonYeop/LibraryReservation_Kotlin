package com.example.libraryreservation_kotlin.auth

import com.example.libraryreservation_kotlin.auth.dto.LoginDto
import com.example.libraryreservation_kotlin.auth.dto.LoginResponseDto
import com.example.libraryreservation_kotlin.auth.dto.SignupDto
import com.example.libraryreservation_kotlin.common.entity.TokenEntity
import com.example.libraryreservation_kotlin.common.entity.UserEntity
import com.example.libraryreservation_kotlin.common.errorHandle.CustomException
import com.example.libraryreservation_kotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryreservation_kotlin.common.jwt.JwtUtil
import com.example.libraryreservation_kotlin.common.repository.TokenRepository
import com.example.libraryreservation_kotlin.common.repository.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Primary
@Service
class AuthService {
    @Value("\${jwt.secret_key}")
    private lateinit var secretKey: String
    private val jwtUtil = JwtUtil()
    private val log = LoggerFactory.getLogger(AuthService::class.java)

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var encoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var tokenRepository: TokenRepository

    @Transactional
    fun login(loginDto: LoginDto): LoginResponseDto {
        val userEntity: UserEntity = userRepository.findByPhoneNumber(loginDto.phoneNumber)
            ?: throw AccessDeniedException("전화번호가 일치하지 않습니다.")

        if (!encoder.matches(loginDto.password, userEntity.password)) {
            throw AccessDeniedException("비밀번호가 일치하지 않습니다.")
        }

        val accessToken: String = jwtUtil.generateToken(userEntity, secretKey)
        val refreshToken: String = jwtUtil.createRefreshToken(secretKey)

        val tokenEntity: TokenEntity = tokenRepository.findByUser(userEntity)
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
}