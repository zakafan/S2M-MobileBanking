package com.example.s2m.repository

import com.example.s2m.model.*
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.viewmodel.LoginViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AddBeneficiaryRepository(loginViewModel: LoginViewModel) {

    private var client =ApiClient.client
    private val tok = loginViewModel.token

    suspend fun addBeneficiary(benefName:String,phone:String):AddBeneficiaryResponse?{
        val data = AddBeneficiaryRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"
            ),
            beneficiaryName = benefName,
            beneficiaryPhoneNumber = "+212$phone",
            beneficiaryType = "WALLET_INT",
            beneficiaryAccountNumber = "",
            beneficiaryCardNumber = "",
            pin = "string"
        )
        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/beneficiary/addBeneficiary"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<AddBeneficiaryResponse?>()
    }

    suspend fun suspendBeneficiary(phone:String):SuspendBeneficiaryResponse?{
        val data = SuspendBeneficiaryRequest(
            loggedRequest = LoggedRequest(
                headerRequest = InitHeader.headerRequest,
                customerAgentId = "222228974132",
                login = "+212778888883",
                phoneNumber = "+212778888883"
            ),
            beneficiaryPhoneNumber = phone,
            beneficiaryAccountNumber = "",
            pin = ""
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/beneficiary/suspendBeneficiary"){
            contentType(ContentType.Application.Json)
            setBody(data)
            header("Authorization", "Bearer ${tok.value.accessToken}")
        }
        return response.body<SuspendBeneficiaryResponse?>()
    }
}