package com.example.libraryReservationKotlin.common.routingDataSource

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!test")
@Component
class DataBaseProperty(
    @Value("\${spring.datasource.writer.hikari.url}")
    val writerUrl: String,
    @Value("\${spring.datasource.writer.hikari.username}")
    val writerUsername: String,
    @Value("\${spring.datasource.writer.hikari.password}")
    var writerPassword: String,
    @Value("\${spring.datasource.reader.hikari.url}")
    val readerUrl: String,
    @Value("\${spring.datasource.reader.hikari.username}")
    val readerUsername: String,
    @Value("\${spring.datasource.reader.hikari.password}")
    val readerPassword: String,
    @Value("\${spring.datasource.driver-class-name}")
    val driverClassName: String,
)
