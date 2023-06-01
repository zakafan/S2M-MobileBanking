package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetAlertRequest (
    @SerialName("loggedRequest")
     val loggedRequest: LoggedRequest,
    @SerialName("page")
    val page:Int

        )