package com.example.libraryReservationKotlin.reservation

import com.example.libraryReservationKotlin.common.dto.ReservationDeleteDto
import com.example.libraryReservationKotlin.common.dto.ReservationDto
import com.example.libraryReservationKotlin.common.entity.ReservationEntity
import com.example.libraryReservationKotlin.common.entity.RoomEntity
import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import com.example.libraryReservationKotlin.common.repository.ReservationRepository
import com.example.libraryReservationKotlin.common.repository.RoomRepository
import com.example.libraryReservationKotlin.common.repository.UserRepository
import com.example.libraryReservationKotlin.common.utils.SecurityUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
) {
    val securityUtil = SecurityUtil()

    @Transactional
    fun reservationSeat(
        reservationDto: ReservationDto,
    ) {
        val startTime = reservationDto.startTime
        val endTime = reservationDto.endTime
        val roomEnum = reservationDto.roomType
        val seatNumber = reservationDto.seatNumber

        val roomEntityList = roomRepository.findByRoomType(roomEnum)

        if (roomEntityList.isEmpty()) {
            throw CustomException(CommunalResponse.ROOM_NOT_FOUND)
        }

        val roomEntity = findSeatNumber(roomEntityList, seatNumber)
            ?: throw CustomException(CommunalResponse.SEAT_NUMBER_NOT_FOUND)

        val userEntity = userRepository.findByPhoneNumber(securityUtil.getCurrentMemberId())
            ?: throw CustomException(CommunalResponse.USER_NOT_FOUND)

        if (reservationRepository.findByUser(userEntity).isNotEmpty()) {
            throw CustomException(CommunalResponse.ALREADY_RESERVATION_USER)
        }

        reservationRepository.findByRoomAndStartTime(roomEntity, startTime)?.let {
            throw CustomException(CommunalResponse.ALREADY_RESERVATION_SEAT)
        }

        val reservationEntity = ReservationEntity(userEntity, roomEntity, startTime, endTime)

        reservationRepository.save(reservationEntity)
    }

    @Transactional(readOnly = true)
    fun getReservationList(
        pageRequest: CustomPageRequest,
    ): List<ReservationEntity> {
        val userEntity = userRepository.findByPhoneNumber(securityUtil.getCurrentMemberId())
            ?: throw CustomException(CommunalResponse.USER_NOT_FOUND)
        return reservationRepository.findByUser(pageRequest, userEntity)
    }

    @Transactional
    fun deleteReservation(
        reservationDeleteDto: ReservationDeleteDto,
    ) {
        val id = reservationDeleteDto.id

        val reservationEntity = reservationRepository.findReservationById(id)
            ?: throw CustomException(CommunalResponse.RESERVATION_NOT_FOUND)

        val userEntity = userRepository.findByPhoneNumber(securityUtil.getCurrentMemberId())
            ?: throw CustomException(CommunalResponse.USER_NOT_FOUND)

        if (reservationEntity.user == userEntity) {
            throw CustomException(CommunalResponse.USER_NOT_CORRECT)
        }

        reservationRepository.save(reservationEntity.also { it.softDelete() })
    }

    private fun findSeatNumber(
        seatList: List<RoomEntity>,
        seatNumber: Int,
    ): RoomEntity? {
        for (roomEntity in seatList) {
            if (roomEntity.seatNumber == seatNumber) {
                return roomEntity
            }
        }
        return null
    }
}
