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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class AdminControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val session: MockHttpSession = MockHttpSession()
    private val authFixtures: AuthFixtures = AuthFixtures()

    @Before
    fun setUp() = session.setAttribute("accessToken", authFixtures.accessTokenOne())

    @After
    fun clean() = session.clearAttributes()

    @DisplayName("예약리스트")
    @Test
    fun reservationTest() {
        mockMvc.perform(
            get("/admin/reservation")
                .param("page", "0")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isOk(),
            jsonPath("$").isArray(),
            jsonPath("$[0].id").value(1),
            jsonPath("$[0].room.id").value(1),
            jsonPath("$[0].room.roomType").value("DIGITAL"),
            jsonPath("$[0].room.seatNumber").value(1),
            jsonPath("$[0].startTime").value("2024-06-04T00:00:00"),
            jsonPath("$[0].endTime").value("2024-06-04T01:00:00"),
        )
    }

    @DisplayName("예약리스트 - 페이지 기본값")
    @Test
    fun reservationPageTest() {
        mockMvc.perform(
            get("/admin/reservation")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isOk(),
            jsonPath("$").isArray(),
            jsonPath("$[0].id").value(1),
            jsonPath("$[0].room.id").value(1),
            jsonPath("$[0].room.roomType").value("DIGITAL"),
            jsonPath("$[0].room.seatNumber").value(1),
            jsonPath("$[0].startTime").value("2024-06-04T00:00:00"),
            jsonPath("$[0].endTime").value("2024-06-04T01:00:00"),
        )
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    fun deleteSuccessTest() {
        mockMvc.perform(
            delete("/admin/reservation")
                .param("id", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(status().isOk())
    }

    @DisplayName("예약 삭제 - null")
    @Test
    fun deleteNullTest() {
        mockMvc.perform(
            delete("/admin/reservation")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("타입이 잘못되었습니다."),
        )
    }

    @DisplayName("예약 삭제 - 없는 인덱스")
    @Test
    @Throws(java.lang.Exception::class)
    fun deleteWrongIndexTest() {
        mockMvc.perform(
            delete("/admin/reservation")
                .param("id", "9999")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("예약을 찾을 수 없습니다."),
        )
    }

    @DisplayName("예약 삭제 - 빈칸")
    @Test
    @Throws(java.lang.Exception::class)
    fun deleteBlankTest() {
        mockMvc.perform(
            delete("/admin/reservation")
                .param("id", "")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("타입이 잘못되었습니다."),
        )
    }

    @DisplayName("예약 삭제 - 마이너스 인덱스")
    @Test
    @Throws(java.lang.Exception::class)
    fun deleteMinusTest() {
        mockMvc.perform(
            delete("/admin/reservation")
                .param("id", "-1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("값이 음수입니다."),
        )
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    @Throws(java.lang.Exception::class)
    fun deleteSpecialCharacterTest() {
        mockMvc.perform(
            delete("/admin/reservation")
                .param("id", "*")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("타입이 잘못되었습니다."),
        )
    }
}
