package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetUnreadedAlertRequest (
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest
)