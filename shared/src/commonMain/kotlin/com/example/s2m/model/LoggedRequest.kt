package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoggedRequest (
    @SerialName("headerRequest")
    private val headerRequest: HeaderRequest,
    @SerialName("customerAgentId")
    private val customerAgentId:String,
    @SerialName("login")
    private val login:String,
    @SerialName("phoneNumber")
    private val phoneNumber:String
        )