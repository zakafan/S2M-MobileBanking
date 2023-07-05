package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawlRequest(
    @SerialName("transactionRequest")
    private val transactionRequest: TransactionRequest,
    @SerialName("qrIndicator")
    private val qrIndicator: String

)
