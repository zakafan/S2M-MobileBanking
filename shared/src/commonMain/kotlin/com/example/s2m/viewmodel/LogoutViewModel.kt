package com.example.s2m.viewmodel

import com.example.s2m.repository.LogoutRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class LogoutViewModel(private val repository: LogoutRepository):ViewModel() {

    private val _loggedOut: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loggedOut: StateFlow<Boolean> = _loggedOut.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun logOut(){
        _loggedOut.value = false
    }
    fun logout(){
        _isLoading.value = true
        viewModelScope.launch {
            val response = async {
                repository.logout()
            }
                println(response.await()?.responseCode)
            if(response.await()?.responseCode=="000"){
                _isLoading.value = false
                _loggedOut.value=true
            }
        }

    }
}