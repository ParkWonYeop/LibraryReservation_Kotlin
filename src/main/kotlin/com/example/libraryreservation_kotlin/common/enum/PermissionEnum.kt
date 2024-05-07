package com.example.libraryreservation_kotlin.common.enum

import lombok.AllArgsConstructor

enum class PermissionEnum(var permission: String) {
    USER("user"),
    ADMIN("admin");
}