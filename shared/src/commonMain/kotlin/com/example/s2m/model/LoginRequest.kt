package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (
    @SerialName("headerRequest")
    val headerRequest: HeaderRequest,
    @SerialName("password")
    val password:String,
    @SerialName("login")
    val login:String,
    @SerialName("serialNumber")
    val serialNumber:String
        )

