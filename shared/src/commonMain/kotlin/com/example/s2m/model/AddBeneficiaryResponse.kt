package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class AddBeneficiaryResponse (
    @SerialName("responseCode")
    var responseCode:String,
    @SerialName("responseDescription")
    var  responseDescription:String
        )