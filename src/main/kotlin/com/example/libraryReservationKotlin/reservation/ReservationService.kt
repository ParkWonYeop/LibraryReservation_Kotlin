package com.example.libraryReservationKotlin.reservation

import com.example.libraryReservationKotlin.common.entity.ReservationEntity
import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.entity.UserEntity
import com.example.libraryReservationKotlin.common.enum.RoomEnum
import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.repository.ReservationRepository
import com.example.libraryReservationKotlin.common.repository.RoomRepository
import com.example.libraryReservationKotlin.common.repository.UserRepository
import com.example.libraryReservationKotlin.common.utils.SecurityUtil
import com.example.libraryReservationKotlin.reservation.dto.ReservationDeleteDto
import com.example.libraryReservationKotlin.reservation.dto.ReservationDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    val reservationRepository: ReservationRepository,
    val roomRepository: RoomRepository,
    val userRepository: UserRepository,
) {
    val securityUtil = SecurityUtil()

    @Transactional
    fun reservationSeat(reservationDto: ReservationDto) {
        val startTime = reservationDto.startTime
        val endTime = reservationDto.endTime

        val roomEnum: RoomEnum = reservationDto.roomType

        val roomEntityList: List<RoomEntity> = roomRepository.findByRoomType(roomEnum)

        if (roomEntityList.isEmpty()) {
            throw CustomException(CommunalResponse.ROOM_NOT_FOUND)
        }

        val roomEntity: RoomEntity = findSeatNumber(roomEntityList, reservationDto.seatNumber)
            ?: throw CustomException(CommunalResponse.SEAT_NUMBER_NOT_FOUND)

        val userEntity: UserEntity = userRepository.findByPhoneNumber(securityUtil.getCurrentMemberId())
            ?: throw CustomException(CommunalResponse.USER_NOT_FOUND)

        if (reservationRepository.findByUser(userEntity).isNotEmpty()) {
            throw CustomException(CommunalResponse.ALREADY_RESERVATION_USER)
        }

        if (reservationRepository.findByRoomAndStartTime(roomEntity, startTime) != null) {
            throw CustomException(CommunalResponse.ALREADY_RESERVATION_SEAT)
        }

        val reservationEntity = ReservationEntity(null, userEntity, roomEntity, startTime, endTime)

        reservationRepository.save(reservationEntity)
    }

    @Transactional(readOnly = true)
    fun getReservationList(): List<ReservationEntity> {
        val userEntity: UserEntity = userRepository.findByPhoneNumber(securityUtil.getCurrentMemberId())
            ?: throw CustomException(CommunalResponse.USER_NOT_FOUND)
        return reservationRepository.findByUser(userEntity)
    }

    @Transactional
    fun deleteReservation(reservationDeleteDto: ReservationDeleteDto) {
        val reservationEntity: ReservationEntity = reservationRepository.findByReservationId(reservationDeleteDto.id)
            ?: throw CustomException(CommunalResponse.RESERVATION_NOT_FOUND)

        val userEntity: UserEntity = userRepository.findByPhoneNumber(securityUtil.getCurrentMemberId())
            ?: throw CustomException(CommunalResponse.USER_NOT_FOUND)

        if (reservationEntity.user != userEntity) {
            throw CustomException(CommunalResponse.USER_NOT_CORRECT)
        }

        reservationRepository.delete(reservationEntity)
    }

    private fun findSeatNumber(seatList: List<RoomEntity>, seatNumber: Int): RoomEntity? {
        for (roomEntity in seatList) {
            if (roomEntity.seatNumber == seatNumber) {
                return roomEntity
            }
        }
        return null
    }
}
