package com.example.libraryreservation_kotlin.common.entity

import com.example.libraryreservation_kotlin.common.enum.RoomEnum
import jakarta.persistence.*

@Entity(name = "room")
class RoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var roomId: Long? = null,

    @Column(name = "room_type", nullable = false)
    var roomEnum: RoomEnum,

    @Column(name = "seat_number", nullable = false)
    var seatNumber: Int? = null
)