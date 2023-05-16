package com.example.s2m.model

@kotlinx.serialization.Serializable
data class InstitutionCurrencies (

    var curAlphaCode:String,
    val curDefaNumbDeci:Int,
    val curIde:String,
    val curLabe:String,
        )
