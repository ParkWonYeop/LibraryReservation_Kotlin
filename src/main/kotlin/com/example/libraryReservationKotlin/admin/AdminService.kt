package com.example.libraryReservationKotlin.admin

import com.example.libraryReservationKotlin.common.dto.ReservationDeleteDto
import com.example.libraryReservationKotlin.common.entity.ReservationEntity
import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.pageable.CustomPageRequest
import com.example.libraryReservationKotlin.common.repository.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val reservationRepository: ReservationRepository,
) {
    @Transactional(readOnly = true)
    fun getReservationList(pageRequest: CustomPageRequest): List<ReservationEntity> = reservationRepository.findAll(pageRequest).content

    @Transactional
    fun deleteReservation(reservationDeleteDto: ReservationDeleteDto) {
        val id = reservationDeleteDto.getNotNullId()
        val reservationEntity = reservationRepository.findReservationById(id)
            ?: throw CustomException(CommunalResponse.RESERVATION_NOT_FOUND)

        return reservationRepository.delete(reservationEntity)
    }
}
