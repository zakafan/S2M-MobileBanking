package com.example.s2m.repository

import com.example.bankingapp.data.model.*
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.model.LoggedRequest
import com.example.s2m.model.TransactionRequest
import com.example.s2m.model.TransferRequest
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class TransferRepository(loginViewModel: LoginViewModel) {
    private val client = ApiClient.client
    private val tok = loginViewModel.token



    suspend fun transfer(amount:String,memo:String,toPhone:String,pin:String): TransferResponse?{

        val data = TransferRequest(
            typeTransfert = "W2W",
            TransactionRequest(
                amount = amount,
                toCurrencyIsoCode = "887",
                pin = pin,
                toName = "",
                toPhone =toPhone,
                fromAccount ="88700022222828302756816887" ,
                memo =memo ,
                fromCurrencyIsoCode = "887",
                loggedRequest = LoggedRequest(
                    headerRequest = InitHeader.headerRequest,
                    customerAgentId = "222228974132",
                    login = "+212778888883",
                    phoneNumber = "+212778888883"
                )
            )
        )


            val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/financial/Transfer"){
                contentType(ContentType.Application.Json)
                setBody(data)
                header("Authorization", "Bearer ${tok.value.accessToken}")
            }
            return response.body<TransferResponse?>()


    }
}