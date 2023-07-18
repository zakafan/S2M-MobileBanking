package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MerchantPaymentRequest (
    @SerialName("transactionRequest")
    private val transactionRequest: TransactionRequest,
    @SerialName("agentId")
    private val agentId : String,
    @SerialName("agentCity")
    private val agentCity: String,
    @SerialName("agentCountry")
    private val agentCountry: String,
    @SerialName("mcc")
    private val mcc:String,
    @SerialName("typeAccount")
    private val typeAccount:String,
    @SerialName("qrIndicator")
    private val qrIndicator: String
        )