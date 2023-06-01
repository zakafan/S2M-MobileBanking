package com.example.s2m.viewmodel

import com.example.s2m.repository.LogoutRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class LogoutViewModel(private val repository: LogoutRepository):ViewModel() {

    private val _loggedOut: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val loggedOut: StateFlow<Boolean> = _loggedOut.asStateFlow()

    fun logout():Boolean{
        runBlocking {
            val response = async {
                repository.logout()
            }

                println(response.await()?.responseCode)
            if(response.await()?.responseCode=="000"){
                _loggedOut.value=true
                println("yessir")
            }

        }

        println(_loggedOut.value)
        return _loggedOut.value
    }
}