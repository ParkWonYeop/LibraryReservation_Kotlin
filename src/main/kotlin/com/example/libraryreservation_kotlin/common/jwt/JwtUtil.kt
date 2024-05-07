package com.example.libraryreservation_kotlin.common.jwt

import com.example.libraryreservation_kotlin.common.entity.UserEntity
import com.example.libraryreservation_kotlin.common.enum.JwtErrorEnum
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import java.util.*

class JwtUtil {
    private val accessExpiredMs: Long = (1000 * 60 * 30).toLong()
    private val refreshExpiredMs: Long = (1000 * 60 * 60 * 3).toLong()

    fun generateToken(user: UserEntity, secretKey: String): String {
        return createAccessToken(user.phoneNumber, secretKey)
    }

    private fun createAccessToken(phoneNumber: String, secretKey: String): String {
        return Jwts.builder()
            .subject(phoneNumber)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + accessExpiredMs))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .compact()
    }

    fun createRefreshToken(secretKey: String?): String {
        return Jwts.builder()
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + refreshExpiredMs))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .compact()
    }

    fun isExpired(token: String?, secretKey: String?): Boolean {
        try {
            return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .payload
                .expiration
                .before(Date())
        } catch (e: SignatureException) {
            throw JwtException(JwtErrorEnum.WRONG_TYPE_TOKEN.msg)
        } catch (e: MalformedJwtException) {
            throw JwtException(JwtErrorEnum.UNSUPPORTED_TOKEN.msg)
        } catch (e: ExpiredJwtException) {
            throw JwtException(JwtErrorEnum.EXPIRED_TOKEN.msg)
        } catch (e: IllegalArgumentException) {
            throw JwtException(JwtErrorEnum.UNKNOWN_ERROR.msg)
        }
    }

    fun getSubject(token: String?, secretKey: String?): String {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }
}