package com.example.libraryReservationKotlin.common.repository

import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.enum.RoomEnum
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<RoomEntity, Long> {
    fun findByRoomType(roomEnum: RoomEnum): List<RoomEntity>
}
