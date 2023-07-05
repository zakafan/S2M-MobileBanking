package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest (
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest,
    @SerialName("password")
    val password:String,
    @SerialName("newPassword")
    val newPassword:String,
    @SerialName("confirmNewPassword")
    val confirmNewPassword:String,
    @SerialName("pin")
    val pin:String

        )