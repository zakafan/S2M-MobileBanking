package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class User(
    @SerialName("responseLogin")
    var responseLogin: ResponseLogin?
)
