package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForexRequest (
    @SerialName("headerRequest")
    private var headerRequest: HeaderRequest
        )