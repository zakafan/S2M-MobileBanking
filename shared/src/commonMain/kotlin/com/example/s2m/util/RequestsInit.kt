package com.example.s2m.util

import com.example.s2m.model.DeviceInfo
import com.example.s2m.model.HeaderRequest


object InitHeader{


private val device = DeviceInfo("01",null,"01",null,null,null,null)
val headerRequest = HeaderRequest("ANDROID","1.1","C",1679524222300,2,"8870002", device )
}