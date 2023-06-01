package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class AlertReadedRequest (
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest,
    @SerialName("alertCode")
    val alertCode:Int
        )