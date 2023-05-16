package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class TransferRequest (
    @SerialName("typeTransfert")
    private val typeTransfert:String,
    @SerialName("transactionRequest")
    private val transactionRequest: TransactionRequest
        )