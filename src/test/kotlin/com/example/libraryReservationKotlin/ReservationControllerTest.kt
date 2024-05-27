package com.example.libraryReservationKotlin

import com.example.libraryReservationKotlin.fixture.AuthFixtures
import com.example.libraryReservationKotlin.fixture.ReservationFixtures
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class ReservationControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    private var session: MockHttpSession = MockHttpSession()
    private val authFixtures: AuthFixtures = AuthFixtures()
    private val reservationFixtures: ReservationFixtures = ReservationFixtures()

    @Before
    fun setUp() {
        session.setAttribute("accessToken", authFixtures.accessTokenOne())
        session.setAttribute("accessToken2", authFixtures.accessTokenTwo())
    }

    @After
    fun cleanSession() = session.clearAttributes()

    @DisplayName("예약 테스트 - 성공")
    @Test
    @Throws(Exception::class)
    fun addSuccessTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/reservation")
                .content(objectMapper.writeValueAsString(reservationFixtures.createReservationSeatNumber("4")))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")),
        )
            .andExpectAll(MockMvcResultMatchers.status().isCreated())
    }

    @DisplayName("예약 테스트 - roomType")
    @Test
    @Throws(Exception::class)
    fun addRoomTypeTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/reservation")
                .content(objectMapper.writeValueAsString(reservationFixtures.createReservationRoomType("STUDYINGs")))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }

    @DisplayName("예약 테스트 - seatNumber/문자")
    @Test
    @Throws(Exception::class)
    fun addSeatNumberStringTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/reservation")
                .content(objectMapper.writeValueAsString(reservationFixtures.createReservationSeatNumber("a")))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            ).andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("예약 테스트 - seatNumber/특수문자")
    @Test
    @Throws(Exception::class)
    fun addSeatNumberSpecialStringTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/reservation")
                .content(objectMapper.writeValueAsString(reservationFixtures.createReservationSeatNumber("*")))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }

    @DisplayName("예약 테스트 - seatNumber/공백")
    @Test
    @Throws(Exception::class)
    fun addSeatBlankTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/reservation")
                .content(objectMapper.writeValueAsString(reservationFixtures.createReservationSeatNumber(" ")))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("값이 null 입니다."),
            )
    }

    @DisplayName("예약 테스트 - time/past")
    @Test
    @Throws(Exception::class)
    fun addTimePastTest() {
        val startTime = LocalDateTime.now().plusHours(-2).withMinute(0).withSecond(0).withNano(0)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/reservation")
                .content(objectMapper.writeValueAsString(reservationFixtures.createReservationDate(startTime)))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("현재보다 과거입니다."),
            )
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    @Throws(Exception::class)
    fun deleteTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/reservation")
                .param("id", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(MockMvcResultMatchers.status().isOk())
    }

    @DisplayName("예약 삭제 - 문자")
    @Test
    @Throws(Exception::class)
    fun deleteStringTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/reservation")
                .param("id", "a")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    @Throws(Exception::class)
    fun deleteSpecialStringTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/reservation")
                .param("id", "*")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }

    @DisplayName("예약 삭제 - 공백")
    @Test
    @Throws(Exception::class)
    fun deleteBlankTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/reservation")
                .param("id", " ")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("값이 null 입니다."),
            )
    }

    @DisplayName("예약 삭제 - 다른 유저")
    @Test
    @Throws(Exception::class)
    fun deleteOtherUserTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/reservation")
                .param("id", 1.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("유저가 일치 하지 않습니다."),
            )
    }
}
