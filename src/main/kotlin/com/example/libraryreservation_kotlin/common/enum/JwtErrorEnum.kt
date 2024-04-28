package com.example.libraryreservation_kotlin.common.enum

enum class JwtErrorEnum(val msg:String, val code:Int) {
    UNKNOWN_ERROR("UNKNOWN_ERROR", 400),
    WRONG_TYPE_TOKEN("WRONG_TYPE_TOKEN", 400),
    EXPIRED_TOKEN("EXPIRED_TOKEN", 400),
    UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", 400),
    ACCESS_DENIED("ACCESS_DENIED", 400),
    NULL_PARAM("Null Param", 400);
}