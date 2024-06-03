package com.example.libraryReservationKotlin.common.repository

import com.example.libraryReservationKotlin.common.entity.ReservationEntity
import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.entity.UserEntity
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ReservationRepository : JpaRepository<ReservationEntity, Long> {
    @EntityGraph(attributePaths = ["user", "room"], type = EntityGraph.EntityGraphType.FETCH)
    fun findByUser(customPageRequest: CustomPageRequest, user: UserEntity): List<ReservationEntity>

    @EntityGraph(attributePaths = ["user", "room"], type = EntityGraph.EntityGraphType.FETCH)
    fun findByUser(user: UserEntity): List<ReservationEntity>

    @EntityGraph(attributePaths = ["user", "room"], type = EntityGraph.EntityGraphType.FETCH)
    fun findByRoomAndStartTime(room: RoomEntity, startTime: LocalDateTime): ReservationEntity?

    @EntityGraph(attributePaths = ["user", "room"], type = EntityGraph.EntityGraphType.FETCH)
    fun findReservationById(id: Long): ReservationEntity?
}
