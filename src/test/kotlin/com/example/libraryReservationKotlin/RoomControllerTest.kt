package com.example.libraryReservationKotlin

import com.example.libraryReservationKotlin.auth.AuthController
import com.example.libraryReservationKotlin.auth.dto.LoginResponseDto
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {
    @Autowired
    private lateinit var authController: AuthController

    @Autowired
    private lateinit var mockMvc: MockMvc
    private var session: MockHttpSession = MockHttpSession()
    private val authFixtures: AuthFixtures = AuthFixtures()

    @Before
    fun setUp() {
        val tokenEntity: LoginResponseDto = authController.login(authFixtures.loginAddressOne())
        session.setAttribute("accessToken", tokenEntity.accessToken)
    }

    @After
    fun cleanSession() {
        session.clearAttributes()
    }

    @DisplayName("방 리스트")
    @Test
    @Throws(Exception::class)
    fun roomListTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/room/list")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$").isArray(),
                MockMvcResultMatchers.jsonPath("$.[0].roomId").value(1),
                MockMvcResultMatchers.jsonPath("$.[0].roomType").value("DIGITAL"),
                MockMvcResultMatchers.jsonPath("$.[0].seatNumber").value(1),
                MockMvcResultMatchers.jsonPath("$.[18].roomId").value(19),
                MockMvcResultMatchers.jsonPath("$.[18].roomType").value("STUDYING"),
                MockMvcResultMatchers.jsonPath("$.[18].seatNumber").value(8),
            )
    }

    @DisplayName("방 종류")
    @Test
    @Throws(Exception::class)
    fun roomTypeTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/room")
                .param("roomType", "STUDYING")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$").isArray(),
                MockMvcResultMatchers.jsonPath("$.[0].roomId").value(12),
                MockMvcResultMatchers.jsonPath("$.[0].roomType").value("STUDYING"),
                MockMvcResultMatchers.jsonPath("$.[0].seatNumber").value(1),
                MockMvcResultMatchers.jsonPath("$.[7].roomId").value(19),
                MockMvcResultMatchers.jsonPath("$.[7].roomType").value("STUDYING"),
                MockMvcResultMatchers.jsonPath("$.[7].seatNumber").value(8),
            )
    }

    @DisplayName("방 종류 - 오타")
    @Test
    @Throws(Exception::class)
    fun roomTypeErrorTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/room")
                .param("roomType", "STUDYINGs")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }

    @DisplayName("방 종류 - 공백")
    @Test
    @Throws(Exception::class)
    fun roomTypeBlankTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/room")
                .param("roomType", " ")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(MockMvcResultMatchers.status().isBadRequest())
    }

    @DisplayName("방 종류 - 특수 문자")
    @Test
    @Throws(Exception::class)
    fun roomTypeSpecialStringTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/room")
                .param("roomType", "*")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("타입이 잘못되었습니다."),
            )
    }
}
