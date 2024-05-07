package com.example.libraryreservation_kotlin.common.errorHandle

import com.example.libraryreservation_kotlin.common.errorHandle.constant.CommunalResponse
import org.springframework.http.HttpStatus

class CustomException(communalResponse: CommunalResponse): RuntimeException() {
    override var message: String = communalResponse.message
    var httpStatus: HttpStatus = communalResponse.httpStatus
}