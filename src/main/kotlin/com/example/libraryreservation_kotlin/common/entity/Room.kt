package com.example.libraryreservation_kotlin.common.entity

import com.example.libraryreservation_kotlin.common.enum.RoomEnum
import jakarta.persistence.*

@Entity(name = "room")
class Room() {
    constructor(roomEnum: RoomEnum, seatNumber: Int) : this() {
        this.roomEnum = roomEnum
        this.seatNumber = seatNumber
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var roomId: Long? = null

    @Column(name = "room_type", nullable = false)
    lateinit var roomEnum: RoomEnum

    @Column(name = "seat_number", nullable = false)
    var seatNumber: Int? = null
}