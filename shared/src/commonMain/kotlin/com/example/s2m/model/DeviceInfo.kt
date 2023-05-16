package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceInfo(
    @SerialName("osType")
    val osType:String,
    @SerialName("osVersion")
    val osVersion:String?,
    @SerialName("deviceType")
    val deviceType:String,
    @SerialName("imei")
    val imei:String?,
    @SerialName("deviceLat")
    val deviceLat: String?,
    @SerialName("deviceLon")
    val deviceLon: String?,
    @SerialName("deviceId")
    val deviceId: String?

)
