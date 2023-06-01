package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetAlertResponse (
    @SerialName("responseCode")
    var responseCode:String,
    @SerialName("responseDescription")
    var  responseDescription:String,
    @SerialName("alertList")
    var alertList:List<Alert>?=null
        )