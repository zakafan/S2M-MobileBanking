package com.example.s2m.repository

import com.example.s2m.model.LoggedRequest
import com.example.s2m.model.TransactionRequest
import com.example.s2m.model.WithdrawlRequest
import com.example.s2m.model.WithdrawlResponse
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class WithdrawalRepository(loginViewModel: LoginViewModel) {

    private val client: HttpClient = ApiClient.client
    private val tok = loginViewModel.token

    suspend fun withdrawl(amount:String,memo:String,toPhone:String,pin:String,qrIndicator:String):WithdrawlResponse?{

        val data = WithdrawlRequest(
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
            qrIndicator = "NO"
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/financial/CashWithdrawal"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }

        return response.body<WithdrawlResponse?>()

    }
}