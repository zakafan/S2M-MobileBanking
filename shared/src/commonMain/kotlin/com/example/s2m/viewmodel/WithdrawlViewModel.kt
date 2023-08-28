package com.example.s2m.viewmodel

import com.example.s2m.repository.WithdrawalRepository
import com.example.s2m.util.SendMoneyErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WithdrawalViewModel(private val repository: WithdrawalRepository): ViewModel() {

    private val _amount: MutableStateFlow<String> = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _memo: MutableStateFlow<String> = MutableStateFlow("")
    val memo: StateFlow<String> = _memo.asStateFlow()

    private val _toPhone: MutableStateFlow<String> = MutableStateFlow("")
    val toPhone: StateFlow<String> = _toPhone.asStateFlow()

    private val _pin: MutableStateFlow<String> = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _qrIndicator: MutableStateFlow<String> = MutableStateFlow("")
    val qrIndicator: StateFlow<String> = _qrIndicator.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _transactionN: MutableStateFlow<String> = MutableStateFlow("")
    val transactionN: StateFlow<String> = _transactionN.asStateFlow()

    private val _navigateToMain : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val navigateToMain: StateFlow<Boolean> = _navigateToMain.asStateFlow()

    private val _withdrawalState: MutableStateFlow<WithdrawalState> = MutableStateFlow(
        WithdrawalState.Idle
    )
    val withdrawalState : StateFlow<WithdrawalState> = _withdrawalState.asStateFlow()

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
    fun onQrIndicatorChanged(qrIndicator:String){
        _qrIndicator.value=qrIndicator
    }

    fun navigateBackToMain() {
        _navigateToMain.value = true
    }

    // Call this function to indicate that the navigation has been handled
    fun onNavigationHandled() {
        _navigateToMain.value = false
    }

    fun clearState() {
        _amount.value = ""
        _toPhone.value = ""
        _memo.value= ""
        _pin.value=""
        _qrIndicator.value = ""
        _transactionN.value = ""

    }

    fun withdrawl(amount:String,memo:String,toPhone:String,pin:String,qrIndicator:String){

        _isLoading.value = true
        viewModelScope.launch {
            val response = async {
                repository.withdrawl(amount,memo,toPhone,pin,qrIndicator)
            }
            println(response.await()?.responseCode +response.await()?.responseDescription)
            if(response.await()?.responseCode == "000"){
                _transactionN.value = response.await()?.trxReference.toString()
                _isLoading.value = false
                _withdrawalState.value =WithdrawalState.Success
            }else if(response.await()?.responseCode == ""){
            }
        }
    }

    sealed class WithdrawalState {
        object Idle : WithdrawalState()
        object  Success : WithdrawalState()
        object Loading : WithdrawalState()
        data class Error( val errorType: SendMoneyErrorType) : WithdrawalState()

    }
}