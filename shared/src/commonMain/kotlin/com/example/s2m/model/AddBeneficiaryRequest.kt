package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class AddBeneficiaryRequest(
    @SerialName("loggedRequest")
    val loggedRequest: LoggedRequest,
    @SerialName("beneficiaryName")
    val beneficiaryName:String,
    @SerialName("beneficiaryPhoneNumber")
    val beneficiaryPhoneNumber:String,
    @SerialName("beneficiaryType")
    val beneficiaryType:String,
    @SerialName("beneficiaryAccountNumber")
    val beneficiaryAccountNumber:String,
    @SerialName("beneficiaryCardNumber")
    val beneficiaryCardNumber:String,
    @SerialName("pin")
    val pin:String
)
