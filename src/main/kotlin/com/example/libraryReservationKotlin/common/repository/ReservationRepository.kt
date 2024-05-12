package com.example.libraryReservationKotlin.common.repository

import com.example.libraryReservationKotlin.common.entity.ReservationEntity
import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ReservationRepository : JpaRepository<ReservationEntity, Long> {
    fun findByUser(user: UserEntity): List<ReservationEntity>
    fun findByRoomAndStartTime(room: RoomEntity, startTime: LocalDateTime): ReservationEntity?
    fun findByReservationId(reservationId: Long): ReservationEntity?
}
