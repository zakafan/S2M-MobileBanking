package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alert (
    @SerialName("messageType")
    var messageType:String,
    @SerialName("iden")
    var iden:Int,
    @SerialName("readed")
    var readed:Boolean,
    @SerialName("messageTxt")
    var messageTxt:String,
    @SerialName("readedDate")
    var readedDate:String?,
    @SerialName("date")
    var date:String,
    @SerialName("alertType")
    var alertType:String,
    @SerialName("messageTypeIden")
    var messageTypeIden:String
        )