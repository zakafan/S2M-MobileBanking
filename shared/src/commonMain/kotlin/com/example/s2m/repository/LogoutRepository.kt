package com.example.s2m.repository

import com.example.s2m.model.LoggedRequest
import com.example.s2m.model.LogoutRequest
import com.example.s2m.model.LogoutResponse
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*

class LogoutRepository(loginViewModel: LoginViewModel) {

    private val client = ApiClient.client
    private val tok = loginViewModel.token

    suspend fun logout():LogoutResponse?{
        val data = LogoutRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"),
            serialNumber = "549800b1-5f0f-4495-8297-69cb37e99894"
        )

       val response= client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/authentication/logout"){
            contentType(ContentType.Application.Json)
            setBody(data)
           header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<LogoutResponse?>()
    }
}

