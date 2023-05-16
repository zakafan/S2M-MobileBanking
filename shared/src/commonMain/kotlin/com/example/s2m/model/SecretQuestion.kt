package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SecretQuestion(
    @SerialName("id")
    val id:Long,
    @SerialName("key")
    val key :String,
    @SerialName("value")
    val value:String
)
