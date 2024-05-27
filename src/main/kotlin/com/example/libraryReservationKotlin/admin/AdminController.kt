package com.example.libraryReservationKotlin.admin

import com.example.libraryReservationKotlin.common.dto.ReservationDeleteDto
import com.example.libraryReservationKotlin.common.validation.ValidationSequence
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(private val adminService: AdminService) {
    @GetMapping("/reservation")
    fun getReservationList(@RequestParam(name = "page", defaultValue = "0") pageNo: Int) = adminService.getReservationList(pageNo)

    @DeleteMapping("/reservation")
    fun deleteReservationList(
        @Validated(ValidationSequence::class)
        reservationDeleteDto: ReservationDeleteDto,
    ) = adminService.deleteReservation(reservationDeleteDto)
}
