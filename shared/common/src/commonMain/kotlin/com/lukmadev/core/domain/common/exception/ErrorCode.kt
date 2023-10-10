package com.lukmadev.core.domain.common.exception

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ErrorCode {
    @SerialName("LDW-101")
    TokenInvalid,
}
