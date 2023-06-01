package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class SendMoneyRequest(
    @SerialName("transactionRequest")
    val transactionRequest: TransactionRequest,
    @SerialName("identityNumber")
    val identityNumber:String,
    @SerialName("notifyBeneficiary")
    val notifyBeneficiary:Boolean,
    @SerialName("secretCode")
    val secretCode:String,
    @SerialName("type")
    val type:Int
)
