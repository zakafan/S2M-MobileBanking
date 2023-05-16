package com.example.s2m.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Equipment(
    @SerialName("id")
    var id:String,
    @SerialName("balance")
    var balance:Double,
    @SerialName("currencyAlphaCode")
    var currencyAlphaCode:String,
    @SerialName("maskedPan")
    var maskedPan:String?,
    @SerialName("expiryDate")
    var expiryDate:String?,
    @SerialName("nameOnCard")
    var nameOnCard:String?,
    @SerialName("currencyIden")
    var currencyIden:String,
    @SerialName("isDefault")
    var isDefault:Boolean,
    @SerialName("status")
    var status:Int,
    @SerialName("type")
    var type:String,
   /* @SerialName("idWallet")
    var idWallet:Long,


    @SerialName("lastTenTransactionList")
    var transactionList:List<Transaction>,
    @SerialName("defaultStatus")
    var defaultStatus:Boolean,





    private var showBalance:Boolean = false*/
)