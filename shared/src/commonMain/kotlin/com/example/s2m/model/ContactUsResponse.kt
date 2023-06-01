package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class ContactUsResponse (
    @SerialName("responseCode")
    val responseCode:String,
    @SerialName("responseDescription")
    val responseDescription:String
)