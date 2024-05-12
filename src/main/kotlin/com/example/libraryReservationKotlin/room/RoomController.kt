package com.example.libraryReservationKotlin.room

import com.example.libraryReservationKotlin.room.dto.RoomTypeDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room")
class RoomController(val roomService: RoomService) {
    @GetMapping("/list")
    fun getRoomList() = roomService.getRoomList()

    @GetMapping
    fun getRoom(roomTypeDto: RoomTypeDto) = roomService.getRoom(roomTypeDto.roomEnum)
}
