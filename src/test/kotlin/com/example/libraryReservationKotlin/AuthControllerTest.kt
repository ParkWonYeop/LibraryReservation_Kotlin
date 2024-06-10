package com.example.libraryReservationKotlin

import com.example.libraryReservationKotlin.auth.dto.RefreshDto
import com.example.libraryReservationKotlin.fixture.AuthFixtures
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    private val authFixtures: AuthFixtures = AuthFixtures()

    @DisplayName("로그인 - 성공")
    @Test
    fun loginTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressTwo()))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isOk(),
                jsonPath("$.accessToken").isString(),
                jsonPath("$.refreshToken").isString(),
            )
    }

    @DisplayName("로그인 - 빈 전화번호")
    @Test
    fun loginEmptyPhoneTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber("")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 공백")
    @Test
    fun loginBlankTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 특수문자")
    @Test
    fun loginSpecialTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber("*")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("전화번호 형식을 맞춰주세요."),
            )
    }

    @DisplayName("로그인 - 전화번호가 아님")
    @Test
    fun loginNotPhoneNumberTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber("000asd")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("전화번호 형식을 맞춰주세요."),
            )
    }

    @DisplayName("로그인 - 틀린 비밀번호")
    @Test
    fun loginWrongPasswordTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("333333")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("비밀번호가 일치하지 않습니다."),
            )
    }

    @DisplayName("로그인 - 공백 비밀번호")
    @Test
    fun loginPasswordBlankTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 빈 비밀번호")
    @Test
    fun loginPasswordEmptyTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 짧은 비밀번호")
    @Test
    fun loginPasswordShortTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("333")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
        )
    }

    @DisplayName("로그인 - 긴 비밀번호")
    @Test
    fun loginPasswordLongTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("3333333333333")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
        )
    }

    @DisplayName("로그인 - 특수문자")
    @Test
    fun loginPasswordSpecialTest() {
        mockMvc.perform(
            post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("33333*")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
        )
    }

    @DisplayName("회원가입 - 성공")
    @Test
    fun signupTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddress()))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isCreated(),
        )
    }

    @DisplayName("회원가입 - 짧은 번호")
    @Test
    fun signupPhoneNumberShortTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPhoneNumber("111")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("전화번호 형식을 맞춰주세요."),
        )
    }

    @DisplayName("회원가입 - 긴 번호")
    @Test
    fun signupPhoneNumberLongTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPhoneNumber("11111111111111")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("전화번호 형식을 맞춰주세요."),
        )
    }

    @DisplayName("회원가입 - 이미있는 폰번호")
    @Test
    fun signupAlreadyTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPhoneNumber("01099716733")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("이미 가입한 전화번호입니다."),
        )
    }

    @DisplayName("회원가입 - 짧은 비밀번호")
    @Test
    fun signupWrongPasswordTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("33333")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
        )
    }

    @DisplayName("회원가입 - 특수문자")
    @Test
    fun signupSpecialPasswordTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("33333*")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
        )
    }

    @DisplayName("회원가입 - 긴 비밀번호")
    @Test
    fun signupLongPasswordTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("33333333333333")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
        )
    }

    @DisplayName("회원가입 - 공백 비밀번호")
    @Test
    fun signupBlankPasswordTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("빈 문자열 입니다."),
        )
    }

    @DisplayName("회원가입 - 빈 비밀번호")
    @Test
    fun signupEmptyPasswordTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("빈 문자열 입니다."),
        )
    }

    @DisplayName("회원가입 - 빈 이름")
    @Test
    fun signupEmptyNameTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("빈 문자열 입니다."),
        )
    }

    @DisplayName("회원가입 - 공백 이름")
    @Test
    fun signupBlankNameTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("빈 문자열 입니다."),
        )
    }

    @DisplayName("회원가입 - 긴 이름")
    @Test
    fun signupLongNameTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("박박박박박")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("이름을 2~4자 사이로 입력해주세요."),
        )
    }

    @DisplayName("회원가입 - 짧은 이름")
    @Test
    fun signupShortNameTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("박")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("이름을 2~4자 사이로 입력해주세요."),
        )
    }

    @DisplayName("회원가입 - 특수문자 이름")
    @Test
    fun signupSpecialNameTest() {
        mockMvc.perform(
            post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("박원*")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
            jsonPath("$").value("이름을 2~4자 사이로 입력해주세요."),
        )
    }

    @DisplayName("토큰 새로고침 - 성공")
    @Test
    fun tokenTest() {
        mockMvc.perform(
            put("/auth/token")
                .content(objectMapper.writeValueAsString(RefreshDto("01099716733", authFixtures.refreshTokenOne())))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isOk(),
            jsonPath("$.accessToken").isString(),
            jsonPath("$.refreshToken").isString(),
        )
    }

    @DisplayName("토큰 새로고침 - 잘못된 토큰")
    @Test
    fun tokenWrongTokenTest() {
        mockMvc.perform(
            put("/auth/token")
                .content(objectMapper.writeValueAsString(authFixtures.refreshTokenAddress("1234a")))
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(
            status().isBadRequest(),
        )
    }
}
