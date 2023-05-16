package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Transaction(
    @SerialName("date")
    var date:Long,
    @SerialName("trxDescription")
    var trxDescription:String,
    @SerialName("sign")
    var sign:String,
    @SerialName("amount")
    var amount:String,
    @SerialName("currency")
    var currency:String,
    @SerialName("valueDate")
    var valueDate:Long,
    @SerialName("reference")
    var reference:String,
    @SerialName("beneficiary")
    var beneficiary:String,
    @SerialName("transactionType")
    var transactionType:String

)
