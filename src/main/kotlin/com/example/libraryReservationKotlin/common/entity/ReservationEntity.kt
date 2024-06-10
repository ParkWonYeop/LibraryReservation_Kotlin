package com.example.libraryReservationKotlin.common.entity

import com.example.libraryReservationKotlin.common.entity.baseEntity.baseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity(name = "reservation")
class ReservationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    var room: RoomEntity,

    @Column(name = "start_time", nullable = false)
    var startTime: LocalDateTime? = null,

    @Column(name = "end_time", nullable = false)
    var endTime: LocalDateTime? = null,
) : baseEntity() {
    constructor(
        user: UserEntity,
        room: RoomEntity,
        startTime: LocalDateTime,
        endTime: LocalDateTime?,
    ) : this(
        id = null,
        user = user,
        room = room,
        startTime = startTime,
        endTime = endTime,
    )
}
