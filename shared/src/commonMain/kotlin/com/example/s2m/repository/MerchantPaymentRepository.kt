package com.example.s2m.repository

import com.example.s2m.model.*
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MerchantPaymentRepository(loginViewModel: LoginViewModel) {

    private val client: HttpClient = ApiClient.client
    private val tok = loginViewModel.token

    suspend fun sendPayment(amount:String,memo:String,toPhone:String,pin:String,qrIndicator:String):MerchantPaymentResponse?{

        val data = MerchantPaymentRequest(
            transactionRequest =
            TransactionRequest(
                amount = amount,
                toCurrencyIsoCode = "887",
                pin = pin,
                toName = "",
                toPhone ="+212$toPhone",
                fromAccount ="88700022222828302756816887" ,
                memo =memo ,
                fromCurrencyIsoCode = "887",
                loggedRequest = LoggedRequest(
                    headerRequest = InitHeader.headerRequest,
                    customerAgentId = "222228974132",
                    login = "+212778888883",
                    phoneNumber = "+212778888883"
                )
            ),

            agentCity = "",
            agentCountry = "",
            agentId = "",
            mcc = "",
            typeAccount = "0",
            qrIndicator = "NO"
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/financial/SendPayment"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<MerchantPaymentResponse?>()
    }
}