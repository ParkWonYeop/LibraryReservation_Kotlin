package com.example.libraryReservationKotlin.common.repository

import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<RoomEntity, Long> {
    fun findByRoomType(customPageRequest: CustomPageRequest, roomEnum: RoomEnum): List<RoomEntity>
    fun findByRoomType(roomEnum: RoomEnum): List<RoomEntity>
}
