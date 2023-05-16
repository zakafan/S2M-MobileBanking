package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class TransactionRequest (
    @SerialName("amount")
    private val amount:String,
    @SerialName("toCurrencyIsoCode")
    private val toCurrencyIsoCode:String,
    @SerialName("pin")
    private val pin:String,
    @SerialName("toName")
    private val toName:String,
    @SerialName("toPhone")
    private val toPhone:String,
    @SerialName("fromAccount")
    private val fromAccount:String,
    @SerialName("memo")
    private val memo:String,
    @SerialName("fromCurrencyIsoCode")
    private val fromCurrencyIsoCode:String,
    @SerialName("loggedRequest")
    private val loggedRequest: LoggedRequest
        )
