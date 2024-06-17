package com.example.libraryReservationKotlin.common.entity

import com.example.libraryReservationKotlin.common.baseEntity.BaseEntity
import com.example.libraryReservationKotlin.common.enum.RoomEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NOT NULL")
@Entity(name = "room")
class RoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "room_type", nullable = false)
    var roomType: RoomEnum,

    @Column(name = "seat_number", nullable = false)
    var seatNumber: Int,
) : BaseEntity()
