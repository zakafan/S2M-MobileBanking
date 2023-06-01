package com.example.s2m.repository

import com.example.s2m.model.ForexRequest
import com.example.s2m.model.ForexResponse
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ForexRepository {

    private val client:HttpClient = ApiClient.client

    suspend fun getForexRates():ForexResponse?{

        val data = ForexRequest(headerRequest = InitHeader.headerRequest,)

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/generic/1/0/repository/getForexTable"){
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body<ForexResponse?>()
    }
}