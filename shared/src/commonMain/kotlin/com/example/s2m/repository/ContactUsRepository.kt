package com.example.s2m.repository

import com.example.s2m.model.ContactUsRequest
import com.example.s2m.model.ContactUsResponse
import com.example.s2m.util.ApiClient
import com.example.s2m.util.InitHeader
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class ContactUsRepository {

    private val client =ApiClient.client

    suspend fun contactUs(
        subject:String,
        email:String,
        requestType:Int,
        message:String,
        fullName:String,
        phoneNumber:String):ContactUsResponse?{

        val data=ContactUsRequest(
            headerRequest = InitHeader.headerRequest,
            subject=subject,
            email=email,
            requestType = requestType,
            message=message,
            fullName = fullName,
            phoneNumber = "+212$phoneNumber"
        )

        val response = client.post("https://mobile-sandbox.s2m.ma/mobile-web-api/mptf/customer/1/0/contact/sendBoRequest"){
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body<ContactUsResponse?>()

    }
}