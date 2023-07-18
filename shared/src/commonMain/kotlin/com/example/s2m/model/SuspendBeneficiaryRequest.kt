package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuspendBeneficiaryRequest(
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest,
    @SerialName("beneficiaryPhoneNumber")
    val beneficiaryPhoneNumber:String,
    @SerialName("beneficiaryAccountNumber")
    val beneficiaryAccountNumber:String,
    @SerialName("pin")
    val pin:String

)
