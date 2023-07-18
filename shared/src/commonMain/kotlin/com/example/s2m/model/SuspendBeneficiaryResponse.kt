package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuspendBeneficiaryResponse(
    @SerialName("responseCode")
    var responseCode:String,
    @SerialName("responseDescription")
    var  responseDescription:String
)
