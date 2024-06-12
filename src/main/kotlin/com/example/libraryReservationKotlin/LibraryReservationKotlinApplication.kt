package com.example.libraryReservationKotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class LibraryReservationKotlinApplication

fun main(args: Array<String>) {
    runApplication<LibraryReservationKotlinApplication>(*args)
}
