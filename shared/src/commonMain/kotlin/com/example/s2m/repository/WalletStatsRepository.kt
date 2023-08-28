package com.example.s2m.repository

import com.example.s2m.model.LoggedRequest
import com.example.s2m.model.TransferResponse
import com.example.s2m.model.WalletStatsRequest
import com.example.s2m.model.WalletStatsResponse
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class WalletStatsRepository(loginViewModel: LoginViewModel) {

    private val client = ApiClient.client
    private val tok = loginViewModel.token

    suspend fun getWalletStatistics():WalletStatsResponse?{

        val data = WalletStatsRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"
            ),
            accountNumber = "88700022222828302756816887",
            periodicity = 2
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/inquiry/walletStatistics"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }

        return response.body<WalletStatsResponse?>()
    }

}