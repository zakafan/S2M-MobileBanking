package com.example.s2m.viewmodel

import com.example.s2m.repository.SendMoneyRepository
import com.example.s2m.util.SendMoneyErrorType
import com.example.s2m.util.TransferErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class SendMoneyViewModel(private val repository: SendMoneyRepository):ViewModel() {

    private val _amount: MutableStateFlow<String> = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _memo: MutableStateFlow<String> = MutableStateFlow("")
    val memo: StateFlow<String> = _memo.asStateFlow()

    private val _toPhone: MutableStateFlow<String> = MutableStateFlow("")
    val toPhone: StateFlow<String> = _toPhone.asStateFlow()

    private val _pin: MutableStateFlow<String> = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _beneficiaryName: MutableStateFlow<String> = MutableStateFlow("")
    val beneficiaryName: StateFlow<String> = _beneficiaryName.asStateFlow()

    private val _identityNumber: MutableStateFlow<String> = MutableStateFlow("")
    val identityNumber: StateFlow<String> = _identityNumber.asStateFlow()

    private val _notify: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val notify: StateFlow<Boolean> = _notify.asStateFlow()

    private val _secretCode: MutableStateFlow<String> = MutableStateFlow("")
    val secretCode: StateFlow<String> = _secretCode.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _sendMoneyState: MutableStateFlow<SendMoneyState> = MutableStateFlow(
        SendMoneyState.Idle
    )

    fun onAmountChanged(amount:String){
        _amount.value=amount
    }
    fun onMemoChanged(memo:String){
        _memo.value=memo
    }
    fun ontoPhoneChanged(phone:String){
        _toPhone.value=phone
    }
    fun onPinChanged(pin:String){
        _pin.value=pin
    }
    fun onBeneficiaryNameChanged(name:String){
        _beneficiaryName.value=name
    }

    fun onNotifyChanged(notify:Boolean){
        _notify.value=notify
    }

    fun onSecretCodeChanged(secretCode:String){
        _secretCode.value=secretCode
    }

    fun onIdentityNumberChanged(identityNumber:String){
        _identityNumber.value=identityNumber
    }

    fun clearState() {
        _amount.value = ""
        _toPhone.value = ""
        _memo.value= ""
        _pin.value=""
        _notify.value=false
        _identityNumber.value=""
        _secretCode.value=""
        _beneficiaryName.value=""
        _sendMoneyState.value=SendMoneyState.Idle
    }

    fun sendMoney(amount:String,memo:String,toPhone:String,pin:String,toName:String,
                  identityNumber:String,notify:Boolean,secretCode:String){

        runBlocking {
            val response=async {
                repository.sendMoney(amount,memo,toPhone,pin,toName,identityNumber,notify, secretCode)
            }
            if(response.await()?.responseCode  == "000"){
                _sendMoneyState.value = SendMoneyState.Success
            }else{

            }
        }
    }

    sealed class SendMoneyState {
        object Idle : SendMoneyState()
        object  Success : SendMoneyState()
        object Loading : SendMoneyState()
        data class Error( val errorType: SendMoneyErrorType) : SendMoneyState()

    }

}