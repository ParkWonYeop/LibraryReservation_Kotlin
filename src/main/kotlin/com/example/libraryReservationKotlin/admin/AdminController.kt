package com.example.libraryReservationKotlin.admin

import com.example.libraryReservationKotlin.common.dto.ReservationDeleteDto
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import com.example.libraryReservationKotlin.common.validation.ValidationSequence
import org.springframework.data.web.PageableDefault
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(private val adminService: AdminService) {
    @GetMapping("/reservation")
    fun getReservationList(@PageableDefault pageRequest: CustomPageRequest) = adminService.getReservationList(pageRequest)

    @DeleteMapping("/reservation")
    fun deleteReservationList(
        @Validated(ValidationSequence::class)
        reservationDeleteDto: ReservationDeleteDto,
    ) = adminService.deleteReservation(reservationDeleteDto)
}
