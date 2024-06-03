package com.example.libraryReservationKotlin.room

import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import com.example.libraryReservationKotlin.common.validation.ValidationSequence
import com.example.libraryReservationKotlin.room.dto.RoomTypeDto
import org.springframework.data.web.PageableDefault
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room")
class RoomController(private val roomService: RoomService) {
    @GetMapping("/list")
    fun getRoomList(@PageableDefault pageRequest: CustomPageRequest) = roomService.getRoomList(pageRequest)

    @GetMapping
    fun getRoom(@Validated(ValidationSequence::class) roomTypeDto: RoomTypeDto) = roomService.getRoom(roomTypeDto)
}
