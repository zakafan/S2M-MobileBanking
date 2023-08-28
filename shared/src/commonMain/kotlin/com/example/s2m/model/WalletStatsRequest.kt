package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WalletStatsRequest(
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest,
    @SerialName("accountNumber")
    val accountNumber:String,
    @SerialName("periodicity")
    val periodicity:Int
)
