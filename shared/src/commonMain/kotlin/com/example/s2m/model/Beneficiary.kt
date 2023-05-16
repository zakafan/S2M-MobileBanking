package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class Beneficiary (

   // @SerialName("id")
    //var id:Long,
    // @SerialName("iden")
    //    var iden:String="",
    //@SerialName("alphaCode")
    //    var alphaCode:String="",
    //    @SerialName("maskedPan")
    //    var maskedPan:String = "",
    //    @SerialName("pin")
    //    var pin:String = ""
    @SerialName("name")
    var name:String="",
    @SerialName("status")
    var status:String="",
    @SerialName("beneficiaryType")
    var beneficiaryType:String="",
    @SerialName("mobilePhone")
    var mobilePhone:String="",
    @SerialName("accountNumber")
    var accountNumber:String?,



        )