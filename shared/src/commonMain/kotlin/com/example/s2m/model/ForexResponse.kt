package com.example.s2m.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForexResponse (
        @SerialName("responseCode")
         var responseCode:String,
        @SerialName("responseDescription")
         var  responseDescription:String,
        @SerialName("forexList")
         var forexList:List<Forex>?=null

        )