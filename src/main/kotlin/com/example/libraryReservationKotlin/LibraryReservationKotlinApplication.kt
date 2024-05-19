package com.example.libraryReservationKotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.example.libraryReservationKotlin.common.repository"])
@EntityScan(basePackages = ["com.example.libraryReservationKotlin.common.entity"])
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class LibraryReservationKotlinApplication

fun main(args: Array<String>) {
    runApplication<LibraryReservationKotlinApplication>(*args)
}