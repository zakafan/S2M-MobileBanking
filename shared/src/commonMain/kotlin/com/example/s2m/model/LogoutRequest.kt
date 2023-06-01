package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LogoutRequest(
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest,
    @SerialName("serialNumber")
    val serialNumber:String
)
