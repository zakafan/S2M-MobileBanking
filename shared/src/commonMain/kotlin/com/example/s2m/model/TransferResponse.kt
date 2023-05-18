package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class TransferResponse (
    @SerialName("responseCode")
    val responseCode:String,
    @SerialName("responseDescription")
    val responseDescription:String,
    @SerialName("trxReference")
    val trxReference:String?=null

        )
