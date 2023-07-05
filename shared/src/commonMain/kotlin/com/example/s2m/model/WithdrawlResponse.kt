package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WithdrawlResponse(
    @SerialName("responseCode")
     val responseCode:String,
    @SerialName("responseDescription")
     val responseDescription:String,
    @SerialName("trxReference")
     val trxReference:String?=null,
    @SerialName("date")
     val date:Long?=null,
    @SerialName("valueDate")
     val valueDate:Long?=null
)


