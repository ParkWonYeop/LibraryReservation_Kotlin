package com.example.libraryReservationKotlin.reservation

import com.example.libraryReservationKotlin.common.dto.ReservationDeleteDto
import com.example.libraryReservationKotlin.common.dto.ReservationDto
import com.example.libraryReservationKotlin.common.validation.ValidationSequence
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reservation")
class ReservationController(private val reservationService: ReservationService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun reservationSeat(
        @Validated(ValidationSequence::class) @RequestBody reservationDto: ReservationDto,
    ) = reservationService.reservationSeat(reservationDto)

    @GetMapping
    fun getReservationList() = reservationService.getReservationList()

    @DeleteMapping()
    fun deleteReservation(
        @Validated(ValidationSequence::class) reservationDeleteDto: ReservationDeleteDto,
    ) = reservationService.deleteReservation(reservationDeleteDto)
}
