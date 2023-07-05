package com.example.s2m.viewmodel

import com.example.s2m.repository.ProfileRepository
import com.example.s2m.util.ChangePasswordErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repository:ProfileRepository) : ViewModel() {

    private val _oldPassword: MutableStateFlow<String> = MutableStateFlow("")
    val oldPassword: StateFlow<String> = _oldPassword.asStateFlow()

    private val _newPassword: MutableStateFlow<String> = MutableStateFlow("")
    val newPassword : StateFlow<String> = _newPassword.asStateFlow()

    private val _confirmNewPassword: MutableStateFlow<String> = MutableStateFlow("")
    val confirmNewPassword: StateFlow<String> = _confirmNewPassword.asStateFlow()

    private val _pin: MutableStateFlow<String> = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _changePasswordState: MutableStateFlow<ChangePasswordState> = MutableStateFlow(
        ChangePasswordState.Idle
    )

    fun onOldPasswordChanged(oldPassword:String){
        _oldPassword.value=oldPassword
    }

    fun onNewPasswordChanged(newPassword:String){
        _newPassword.value=newPassword
    }

    fun onConfirmNewPasswordChanged(confirmNewPassword:String){
        _confirmNewPassword.value=confirmNewPassword
    }

    fun onPinChanged(pin:String){
        _pin.value=pin
    }

    fun clearState(){
        _oldPassword.value          = ""
        _pin.value                  = ""
        _newPassword.value          = ""
        _confirmNewPassword.value   = ""
        _changePasswordState.value  = ChangePasswordState.Idle
    }

    fun changePassword(oldPassword: String,newPassword: String,confirmNewPassword: String,pin: String):ChangePasswordState{

        runBlocking {
            val response = async {
                repository.changePassword(oldPassword,newPassword,confirmNewPassword,pin)
            }
            println(response.await()?.responseCode + response.await()?.responseDescription)
            if(response.await()?.responseCode == "000"){
                _changePasswordState.value = ChangePasswordState.Success
            }else if (response.await()?.responseCode == "312"){
                _changePasswordState.value = ChangePasswordState.Error(ChangePasswordErrorType.PIN)
            }
        }

        return _changePasswordState.value

    }


    sealed class ChangePasswordState {
        object Idle : ChangePasswordState()
        object  Success : ChangePasswordState()
        object Loading : ChangePasswordState()
        data class Error( val errorType: ChangePasswordErrorType) : ChangePasswordState()

    }
}