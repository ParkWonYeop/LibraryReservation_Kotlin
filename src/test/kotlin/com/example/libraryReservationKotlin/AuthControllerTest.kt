package com.example.libraryReservationKotlin

import com.example.libraryReservationKotlin.auth.AuthController
import com.example.libraryReservationKotlin.auth.dto.LoginResponseDto
import com.example.libraryReservationKotlin.fixture.AuthFixtures
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
class AuthControllerTest {
    @Autowired
    private lateinit var authController: AuthController

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    private var session: MockHttpSession = MockHttpSession()
    private val authFixtures: AuthFixtures = AuthFixtures()

    @Before
    fun setUp() {
        val tokenEntity: LoginResponseDto = authController.login(authFixtures.loginAddressOne())
        session.setAttribute("refreshToken", tokenEntity.refreshToken)
    }

    @After
    fun clean() = session.clearAttributes()

    @DisplayName("로그인 - 성공")
    @Test
    @Throws(Exception::class)
    fun loginTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressOne()))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.accessToken").isString(),
                MockMvcResultMatchers.jsonPath("$.refreshToken").isString(),
            )
    }

    @DisplayName("로그인 - 빈 전화번호")
    @Test
    @Throws(Exception::class)
    fun loginEmptyPhoneTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber("")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 공백")
    @Test
    @Throws(Exception::class)
    fun loginBlankTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 특수문자")
    @Test
    @Throws(Exception::class)
    fun loginSpecialTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber("*")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("전화번호 형식을 맞춰주세요."),
            )
    }

    @DisplayName("로그인 - 전화번호가 아님")
    @Test
    @Throws(Exception::class)
    fun loginNotPhoneNumberTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPhoneNumber("000asd")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("전화번호 형식을 맞춰주세요."),
            )
    }

    @DisplayName("로그인 - 틀린 비밀번호")
    @Test
    @Throws(Exception::class)
    fun loginWrongPasswordTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("333333")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호가 일치하지 않습니다."),
            )
    }

    @DisplayName("로그인 - 공백 비밀번호")
    @Test
    @Throws(Exception::class)
    fun loginPasswordBlankTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 빈 비밀번호")
    @Test
    @Throws(Exception::class)
    fun loginPasswordEmptyTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("로그인 - 짧은 비밀번호")
    @Test
    @Throws(Exception::class)
    fun loginPasswordShortTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("333")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
            )
    }

    @DisplayName("로그인 - 긴 비밀번호")
    @Test
    @Throws(Exception::class)
    fun loginPasswordLongTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("3333333333333")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
            )
    }

    @DisplayName("로그인 - 특수문자")
    @Test
    @Throws(Exception::class)
    fun loginPasswordSpecialTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(authFixtures.loginAddressPassword("33333*")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
            )
    }

    @DisplayName("회원가입 - 성공")
    @Test
    @Throws(Exception::class)
    fun signupTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddress()))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
            )
    }

    @DisplayName("회원가입 - 짧은 번호")
    @Test
    @Throws(Exception::class)
    fun signupPhoneNumberShortTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPhoneNumber("111")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("전화번호 형식을 맞춰주세요."),
            )
    }

    @DisplayName("회원가입 - 긴 번호")
    @Test
    @Throws(Exception::class)
    fun signupPhoneNumberLongTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPhoneNumber("11111111111111")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("전화번호 형식을 맞춰주세요."),
            )
    }

    @DisplayName("회원가입 - 이미있는 폰번호")
    @Test
    @Throws(Exception::class)
    fun signupAlreadyTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPhoneNumber("01099716733")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("이미 가입한 전화번호입니다."),
            )
    }

    @DisplayName("회원가입 - 짧은 비밀번호")
    @Test
    @Throws(Exception::class)
    fun signupWrongPasswordTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("33333")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
            )
    }

    @DisplayName("회원가입 - 특수문자")
    @Test
    @Throws(Exception::class)
    fun signupSpecialPasswordTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("33333*")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
            )
    }

    @DisplayName("회원가입 - 긴 비밀번호")
    @Test
    @Throws(Exception::class)
    fun signupLongPasswordTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("33333333333333")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요."),
            )
    }

    @DisplayName("회원가입 - 공백 비밀번호")
    @Test
    @Throws(Exception::class)
    fun signupBlankPasswordTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("회원가입 - 빈 비밀번호")
    @Test
    @Throws(Exception::class)
    fun signupEmptyPasswordTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressPassword("")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("회원가입 - 빈 이름")
    @Test
    @Throws(Exception::class)
    fun signupEmptyNameTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("회원가입 - 공백 이름")
    @Test
    @Throws(Exception::class)
    fun signupBlankNameTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName(" ")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("빈 문자열 입니다."),
            )
    }

    @DisplayName("회원가입 - 긴 이름")
    @Test
    @Throws(Exception::class)
    fun signupLongNameTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("박박박박박")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("이름을 2~4자 사이로 입력해주세요."),
            )
    }

    @DisplayName("회원가입 - 짧은 이름")
    @Test
    @Throws(Exception::class)
    fun signupShortNameTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("박")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("이름을 2~4자 사이로 입력해주세요."),
            )
    }

    @DisplayName("회원가입 - 특수문자 이름")
    @Test
    @Throws(Exception::class)
    fun signupSpecialNameTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/signup")
                .content(objectMapper.writeValueAsString(authFixtures.signupAddressName("박원*")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$").value("이름을 2~4자 사이로 입력해주세요."),
            )
    }

    @DisplayName("토큰 새로고침 - 성공")
    @Test
    @Throws(Exception::class)
    fun tokenTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/auth/token")
                .content(objectMapper.writeValueAsString(authFixtures.refreshToken(session.getAttribute("refreshToken").toString())))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.accessToken").isString(),
                MockMvcResultMatchers.jsonPath("$.refreshToken").isString(),
            )
    }

    @DisplayName("토큰 새로고침 - 잘못된 토큰")
    @Test
    @Throws(Exception::class)
    fun tokenWrongTokenTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/auth/token")
                .content(objectMapper.writeValueAsString(authFixtures.refreshToken("1234a")))
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
            )
    }
}
