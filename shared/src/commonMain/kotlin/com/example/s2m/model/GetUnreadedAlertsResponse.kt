package com.example.s2m.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
class GetUnreadedAlertsResponse (
    @SerialName("responseCode")
    var responseCode:String,
    @SerialName("responseDescription")
    var  responseDescription:String,
    @SerialName("unReadedAlert")
    var unReadedAlert:Int

)