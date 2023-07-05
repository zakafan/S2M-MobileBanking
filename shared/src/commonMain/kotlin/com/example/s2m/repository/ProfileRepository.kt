package com.example.s2m.repository

import com.example.s2m.model.ChangePasswordRequest
import com.example.s2m.model.ChangePasswordResponse
import com.example.s2m.model.LoggedRequest
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ProfileRepository(loginViewModel: LoginViewModel) {

    private val client = ApiClient.client
    private val tok = loginViewModel.token

    suspend fun changePassword(password:String,newPassword:String,confirmNewPassword:String,pin:String):ChangePasswordResponse?{

        val data = ChangePasswordRequest(
            loggedRequest = LoggedRequest(
                    headerRequest = InitHeader.headerRequest,
                    customerAgentId = "222228974132",
                    login = "+212778888883",
                    phoneNumber = "+212778888883"
            ),
            password = password,
            newPassword = newPassword,
            confirmNewPassword =confirmNewPassword ,
            pin = pin
        )
        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/authentication/changePassword"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<ChangePasswordResponse?>()


    }
}