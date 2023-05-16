package com.example.s2m.model

@kotlinx.serialization.Serializable
data class ProfileCurrency (

        val id:Long,
        val curCode:Int,
        var curAlphaCode:String,
        val curDefaNumbDeci:Int,
        val curIde:String,
        val curLabe:String,
        val selection:Boolean,
        val selectAll:Boolean

        )
