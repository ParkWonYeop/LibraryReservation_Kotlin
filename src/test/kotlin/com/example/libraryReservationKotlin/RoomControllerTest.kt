package com.example.libraryReservationKotlin

import com.example.libraryReservationKotlin.fixture.AuthFixtures
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private var session: MockHttpSession = MockHttpSession()
    private val authFixtures: AuthFixtures = AuthFixtures()

    @Before
    fun setUp() {
        session.setAttribute("accessToken", authFixtures.accessTokenOne())
    }

    @After
    fun cleanSession() {
        session.clearAttributes()
    }

    @DisplayName("방 리스트")
    @Test
    fun roomListTest() {
        mockMvc.perform(
            get("/room/list")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$.[0].id").value(1),
                jsonPath("$.[0].roomType").value("DIGITAL"),
                jsonPath("$.[0].seatNumber").value(1),
                jsonPath("$.[9].id").value(10),
                jsonPath("$.[9].roomType").value("READING"),
                jsonPath("$.[9].seatNumber").value(4),
            )
    }

    @DisplayName("방 종류")
    @Test
    fun roomTypeTest() {
        mockMvc.perform(
            get("/room")
                .param("roomType", "STUDYING")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$.[0].id").value(12),
                jsonPath("$.[0].roomType").value("STUDYING"),
                jsonPath("$.[0].seatNumber").value(1),
                jsonPath("$.[7].id").value(19),
                jsonPath("$.[7].roomType").value("STUDYING"),
                jsonPath("$.[7].seatNumber").value(8),
            )
    }

    @DisplayName("방 종류 - 오타")
    @Test
    fun roomTypeErrorTest() {
        mockMvc.perform(
            get("/room")
                .param("roomType", "STUDYINGs")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }

    @DisplayName("방 종류 - 공백")
    @Test
    fun roomTypeBlankTest() {
        mockMvc.perform(
            get("/room")
                .param("roomType", " ")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(status().isBadRequest())
    }

    @DisplayName("방 종류 - 특수 문자")
    @Test
    fun roomTypeSpecialStringTest() {
        mockMvc.perform(
            get("/room")
                .param("roomType", "*")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }
}
