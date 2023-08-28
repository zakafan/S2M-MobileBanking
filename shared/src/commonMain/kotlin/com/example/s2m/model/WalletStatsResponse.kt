package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WalletStatsResponse(

    @SerialName("responseCode")
    val responseCode:String,
    @SerialName("responseDescription")
    val responseDescription:String,
    @SerialName("walletConsumption")
    var walletConsumption:List<WalletConsumption>?=null

)
