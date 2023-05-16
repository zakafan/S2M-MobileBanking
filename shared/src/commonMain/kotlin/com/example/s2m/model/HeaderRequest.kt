package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeaderRequest(
    @SerialName("appPlateform")
    val appPlatform:String,
    @SerialName("appVersion")
    val appVersion:String,
    @SerialName("appType")
    val appType:String,
    @SerialName("requesTime")
    val requesTime:Long,
    @SerialName("preferredLanguage")
    val preferredLanguage:Int,
    @SerialName("institutionId")
    val institutionId:String,
    @SerialName("deviceInfo")
    val deviceInfo: DeviceInfo

)
