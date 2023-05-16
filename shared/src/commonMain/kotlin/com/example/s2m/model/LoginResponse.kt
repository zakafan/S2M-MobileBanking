package com.example.s2m.model

import com.example.s2m.model.ResponseLogin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("responseCode")
    val responseCode:String,
    @SerialName("responseDescription")
    val responseDescription:String,
    @SerialName("responseLogin")
    val responseLogin: ResponseLogin?=null,
    @SerialName("accessToken")
    var accessToken:String?=null,
   /* @SerialName("refreshToken")
    val refreshToken:String?,

    @SerialName("tokenType")
    val tokenType:String?*/


)
