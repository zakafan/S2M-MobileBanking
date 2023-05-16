package com.example.s2m.viewmodel

import com.example.s2m.model.LoginResponse
import com.example.s2m.model.User
import com.example.s2m.repository.LoginRepository
import com.example.s2m.util.LoginErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(private val repository: LoginRepository):ViewModel() {

    private val _login: MutableStateFlow<String> = MutableStateFlow("")
    val login: StateFlow<String> = _login.asStateFlow()

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _user:MutableStateFlow<User> = MutableStateFlow(User(null))
    val user:StateFlow<User> = _user.asStateFlow()

    private val _token:MutableStateFlow<LoginResponse> = MutableStateFlow(LoginResponse("","",null,null))
    val token:StateFlow<LoginResponse> = _token.asStateFlow()

    var isWelcomeScreenVisible   = true

    fun onLoginTextChanged(login: String) {
        _login.value = login
    }

    fun onPasswordTextChanged(password: String) {
        _password.value = password
    }

    fun clearState() {
        _login.value = ""
        _password.value = ""
        _loginState.value= LoginState.Idle
        _user.value= User(null)

    }

    fun login2(username: String, pass: String): LoginState {

        try {

            runBlocking {
                _isLoading.value = true
                val response = async {
                    repository.login(username, pass)
                }

                _isLoading.value = false
                 _loginState.value =
                     if (response.await()?.responseCode == "000") {
                         _user.value.responseLogin =response.await()?.responseLogin
                         _token.value.accessToken=response.await()?.accessToken
                         println(token.value)
                         LoginState.Success
                     } else {

                         val errorType: LoginErrorType = if (response.await()?.responseCode !== "000") {
                             LoginErrorType.Http
                         } else {

                             LoginErrorType.Api
                         }
                         LoginState.Error(errorType)
                     }
                 }

        }catch (e:IOException){
            _loginState.value = LoginState.Error(LoginErrorType.Connection)
        }
        return _loginState.value
    }


    sealed class LoginState {
        object Idle : LoginState()
        object  Success : LoginState()
        object Loading : LoginState()
        data class Error( val errorType: LoginErrorType) : LoginState()

    }
}





/*  fun login(username: String, pass: String): Boolean {

        runBlocking {
            val response = async {
                repository.login(username, pass)?.responseCode ?: ""

            }
            if (response.await() == "000") {

                status = true

            } else {
                println("False credentials")
            }
        }
        return status
    }*/


