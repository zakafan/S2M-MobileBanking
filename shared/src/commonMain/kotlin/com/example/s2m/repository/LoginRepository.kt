package com.example.s2m.repository

import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import com.example.s2m.model.LoginRequest
import com.example.s2m.model.LoginResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

import io.ktor.http.*


 class LoginRepository() {
      var resp:HttpStatusCode= HttpStatusCode(123456,"Default")



     private val client = ApiClient.client

    suspend fun login(username: String, password: String): LoginResponse? {

        val data = LoginRequest(
            headerRequest = InitHeader.headerRequest,
            password = password ,
            login = "+212$username",
            serialNumber = "549800b1-5f0f-4495-8297-69cb37e99894"
        )



        return try {



            val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/authentication/login"){
                contentType(ContentType.Application.Json)
                setBody(data)
            }
            resp = response.status
            //val responseLogin = response.body<ResponseLogin?>()?.
           // println(responseLogin)
            println(resp)
            return response.body<LoginResponse?>()


        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }




    }
}