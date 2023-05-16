package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class ResponseLogin(
  //  @SerialName("idUser")
   // val idUser:Long,
   // @SerialName("id")
   // val id:String,
    @SerialName("type")
    val type:String,
    @SerialName("language")
    val language:String,
   // @SerialName("secretQuestion")
   // val secretQuestion: SecretQuestion,
    @SerialName("addAdr1")
    val addAdr1:String?,
    @SerialName("addAdr2")
    val addAdr2:String?,
    @SerialName("postalCode")
    val postalCode:String?,
    @SerialName("city")
    val city:String,
    @SerialName("firstName")
    val firstName:String,
    @SerialName("middleName")
    val middleName:String?,
    @SerialName("lastName")
    val lastName:String,
    @SerialName("equipementList")
    val equipmentList:List<Equipment>,
    @SerialName("phone")
    val phone:String,
    @SerialName("beneficiaryList")
    val beneficiaryList:List<Beneficiary>
   /* @SerialName("fourthName")
    val fourthName:String?,
    @SerialName("email")
    val email:String,
    @SerialName("nationality")
    val nationality:String,
    @SerialName("defaultAccount")
    val defaultAccount:String?,
    @SerialName("defaultCurrency")
    val defaultCurrency:String,
    @SerialName("attempCounter")
    val attempCounter:Int,
    @SerialName("deviceStatus")
    val deviceStatus:String,
    @SerialName("terminalNumber")
    val terminalNumber:String?,
    @SerialName("pinEntryIndicator")
    val pinEntryIndicator:Boolean,

    @SerialName("login")
    val login:String,
    @SerialName("photoUrl")
    val photoUrl:String,

    @SerialName("firstLoginInd")
    val firstLoginInd:Boolean,
    @SerialName("tokenLifeTime")
    val tokenLifeTime:Double,
    @SerialName("secretQuestionList")
    val secretQuestionList:List<SecretQuestion>,
    @SerialName("profileCurrencies")
    val profileCurrencies:List<ProfileCurrency>,
    @SerialName("institutionCurrencies")
    val institutionCurrencies:List<InstitutionCurrencies>,
    @SerialName("nbrUnreadAlerts")
    val nbrUnreadAlerts:String,
    */

)
