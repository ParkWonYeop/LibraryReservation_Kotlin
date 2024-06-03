package com.example.libraryReservationKotlin.room

import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import com.example.libraryReservationKotlin.common.repository.RoomRepository
import com.example.libraryReservationKotlin.room.dto.RoomTypeDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(private val roomRepository: RoomRepository) {
    @Transactional(readOnly = true)
    fun getRoomList(pageRequest: CustomPageRequest): List<RoomEntity> = roomRepository.findAll(pageRequest).content

    @Transactional(readOnly = true)
    fun getRoom(roomTypeDto: RoomTypeDto) = roomRepository.findByRoomType(roomTypeDto.getNotNullRoomType())
}
