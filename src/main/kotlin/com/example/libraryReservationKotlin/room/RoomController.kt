package com.example.libraryReservationKotlin.room

import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room")
class RoomController(private val roomService: RoomService) {
    @GetMapping("/list")
    fun getRoomList(
        pageRequest: CustomPageRequest,
    ) = roomService.getRoomList(pageRequest)

    @GetMapping
    fun getRoom(
        @RequestParam
        roomType: RoomEnum,
    ) = roomService.getRoom(roomType)
}
