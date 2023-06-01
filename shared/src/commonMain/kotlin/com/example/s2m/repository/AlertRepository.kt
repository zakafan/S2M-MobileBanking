package com.example.s2m.repository

import com.example.s2m.model.*
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class AlertRepository(loginViewModel: LoginViewModel) {

    private val tok = loginViewModel.token
    private val client = ApiClient.client



    suspend fun readAlert(alertCode:Int):AlertReadedResponse?{
        val data = AlertReadedRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"
            ),
            alertCode = alertCode
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/alert/alertReaded"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<AlertReadedResponse?>()

    }

    suspend fun getUnreadedAlerts():GetUnreadedAlertsResponse?{

        val data = GetUnreadedAlertRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"
            )
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/alert/GetUnreadAlert"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<GetUnreadedAlertsResponse?>()
    }
    suspend fun getAlerts(page:Int): GetAlertResponse? {
        val data= GetAlertRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"
            )
            ,page=page
        )
        val response=client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/alert/GetAlert"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")

        }
        return response.body<GetAlertResponse?>()
    }
}