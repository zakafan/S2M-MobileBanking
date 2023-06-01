package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ContactUsRequest (
    @SerialName("headerRequest")
    private var headerRequest: HeaderRequest,
    @SerialName("subject")
    val subject :String,
    @SerialName("email")
    val email:String,
    @SerialName("requestType")
    val requestType:Int,
    @SerialName("message")
    val message:String,
    @SerialName("fullName")
    val fullName:String,
    @SerialName("phoneNumber")
    val phoneNumber:String
        )