package com.example.libraryReservationKotlin.fixture

import java.time.LocalDateTime

class ReservationFixtures {
    fun createReservationSeatNumber(seatNumber: String): Map<String, String> {
        val body: MutableMap<String, String> = HashMap()

        val startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0)

        body["roomType"] = "STUDYING"
        body["seatNumber"] = seatNumber
        body["startTime"] = startTime.toString()
        body["endTime"] = startTime.plusHours(1).toString()

        return body
    }

    fun createReservationNullSeatNumber(): Map<String, String> {
        val body: MutableMap<String, String> = HashMap()

        val startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0)

        body["roomType"] = "STUDYING"
        body["startTime"] = startTime.toString()
        body["endTime"] = startTime.plusHours(1).toString()

        return body
    }

    fun createReservationRoomType(roomType: String): Map<String, String> {
        val body: MutableMap<String, String> = HashMap()

        val startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0)

        body["roomType"] = roomType
        body["seatNumber"] = "1"
        body["startTime"] = startTime.toString()
        body["endTime"] = startTime.plusHours(1).toString()

        return body
    }

    fun createReservationDate(startTime: LocalDateTime): Map<String, String> {
        val body: MutableMap<String, String> = HashMap()

        body["roomType"] = "STUDYING"
        body["seatNumber"] = "1"
        body["startTime"] = startTime.toString()
        body["endTime"] = startTime.plusHours(1).toString()

        return body
    }
}
