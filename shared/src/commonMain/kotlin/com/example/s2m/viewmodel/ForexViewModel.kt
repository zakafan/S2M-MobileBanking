package com.example.s2m.viewmodel

import com.example.s2m.model.Forex
import com.example.s2m.model.ForexResponse
import com.example.s2m.repository.ForexRepository
import com.example.s2m.util.LoginErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class ForexViewModel(private val repository: ForexRepository):ViewModel() {


    private val _forexList: MutableStateFlow<ForexResponse> = MutableStateFlow(ForexResponse("","", null))
    var forexList: StateFlow<ForexResponse> = _forexList.asStateFlow()
    private val _loginState: MutableStateFlow<LoginViewModel.LoginState> = MutableStateFlow(LoginViewModel.LoginState.Idle)

    fun getForexRate(){

        try {


        runBlocking{
            val response=async {
                repository.getForexRates()
            }
            println(response.await()?.responseCode)
            if (response.await()?.responseCode=="000"){
              _forexList.value.forexList= response.await()!!.forexList
            }else{
                println("error")
            }

        }
    }catch (e:IOException){
            _loginState.value = LoginViewModel.LoginState.Error(LoginErrorType.Connection)
    }
    }

}