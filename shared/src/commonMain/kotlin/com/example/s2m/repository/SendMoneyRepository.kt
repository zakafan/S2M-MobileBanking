package com.example.s2m.repository

import com.example.s2m.model.LoggedRequest
import com.example.s2m.model.SendMoneyRequest
import com.example.s2m.model.SendMoneyResponse
import com.example.s2m.model.TransactionRequest
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class SendMoneyRepository(loginViewModel: LoginViewModel) {

    private val client = ApiClient.client
    private val tok = loginViewModel.token

    suspend fun sendMoney(amount:String,memo:String,toPhone:String,pin:String,toName:String,
                          identityNumber:String,notify:Boolean,secretCode:String
    ): SendMoneyResponse?{
        val data =SendMoneyRequest(
            transactionRequest =
                TransactionRequest(
                    amount = amount,
                    toCurrencyIsoCode = "887",
                    pin = pin,
                    toName = toName,
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
            identityNumber = identityNumber,
            notifyBeneficiary = notify,
            secretCode = secretCode,
            type = 0

        )

       val response= client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/financial/SendMoney"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<SendMoneyResponse?>()
    }
}