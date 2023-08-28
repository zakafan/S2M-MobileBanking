package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class WalletConsumption (
    @SerialName("date")
    val date:String,
    @SerialName("debitMovements")
    val debitMovements:String,
    @SerialName("creditMovements")
    val creditMovements:String

)
