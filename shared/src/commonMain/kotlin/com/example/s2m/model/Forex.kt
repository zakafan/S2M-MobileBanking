package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class Forex (
    @SerialName("sourceCurrencyUnicode")
     var sourceCurrencyUniCode:String?=null,
    @SerialName("sourceCurrencyAlpha3Code")
     var sourceCurrencyAlpha3Code:String?=null,
    @SerialName("purchaseRate")
     var purchaseRate:String,
    @SerialName("saleRate")
     var saleRate:String,
    @SerialName("destinationCurrencyAlpha3code")
     var destinationCurrencyAlpha3Code:String,
    @SerialName("sourceCurrencyLabel")
     var sourceCurrencyLabel:String
        )