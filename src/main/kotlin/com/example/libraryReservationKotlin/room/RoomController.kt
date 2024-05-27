package com.example.libraryReservationKotlin.room

import com.example.libraryReservationKotlin.common.validation.ValidationSequence
import com.example.libraryReservationKotlin.room.dto.RoomTypeDto
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room")
class RoomController(private val roomService: RoomService) {
    @GetMapping("/list")
    fun getRoomList(@RequestParam(name = "page", defaultValue = "0") pageNo: Int) = roomService.getRoomList(pageNo)

    @GetMapping
    fun getRoom(@Validated(ValidationSequence::class) roomTypeDto: RoomTypeDto) = roomService.getRoom(roomTypeDto.roomType)
}
