package com.example.libraryReservationKotlin.admin

import com.example.libraryReservationKotlin.common.entity.ReservationEntity
import com.example.libraryReservationKotlin.common.errorHandle.CustomException
import com.example.libraryReservationKotlin.common.errorHandle.constant.CommunalResponse
import com.example.libraryReservationKotlin.common.repository.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val reservationRepository: ReservationRepository,
) {
    @Transactional(readOnly = true)
    fun getReservationList(): List<ReservationEntity> = reservationRepository.findAll()

    @Transactional
    fun deleteReservation(reservationId: Long) {
        val reservationEntity: ReservationEntity = reservationRepository.findByReservationId(reservationId)
            ?: throw CustomException(CommunalResponse.RESERVATION_NOT_FOUND)

        return reservationRepository.delete(reservationEntity)
    }
}
