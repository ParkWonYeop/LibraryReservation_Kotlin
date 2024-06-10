package com.example.libraryReservationKotlin.common.jwt

import com.example.libraryReservationKotlin.common.entity.UserEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.*

class JwtUtil {
    private val accessExpiredMs: Long = (1000 * 60 * 30).toLong()
    private val refreshExpiredMs: Long = (1000 * 60 * 60 * 3).toLong()

    fun generateToken(
        user: UserEntity,
        secretKey: String,
    ): String = createAccessToken(user.phoneNumber, secretKey)

    private fun createAccessToken(
        phoneNumber: String,
        secretKey: String,
    ) = Jwts.builder()
        .subject(phoneNumber)
        .issuedAt(Date(System.currentTimeMillis()))
        .expiration(Date(System.currentTimeMillis() + accessExpiredMs))
        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
        .compact()

    fun createRefreshToken(
        secretKey: String?,
    ): String = Jwts.builder()
        .issuedAt(Date(System.currentTimeMillis()))
        .expiration(Date(System.currentTimeMillis() + refreshExpiredMs))
        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
        .compact()

    fun isExpired(
        token: String,
        secretKey: String,
    ) = Jwts
        .parser()
        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
        .build()
        .parseSignedClaims(token)
        .payload
        .expiration
        .before(Date(System.currentTimeMillis()))

    fun getSubject(
        token: String?,
        secretKey: String?,
    ): String = Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
        .build()
        .parseSignedClaims(token)
        .payload
        .subject
}
