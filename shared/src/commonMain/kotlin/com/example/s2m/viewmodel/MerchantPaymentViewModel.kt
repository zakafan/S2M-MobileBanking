package com.example.s2m.viewmodel

import com.example.s2m.repository.MerchantPaymentRepository
import com.example.s2m.util.SendMoneyErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MerchantPaymentViewModel(private val repository: MerchantPaymentRepository):ViewModel() {

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

    private val _merchantPaymentState: MutableStateFlow<MerchantPaymentState> = MutableStateFlow(
        MerchantPaymentState.Idle
    )
    val merchantPaymentState : StateFlow<MerchantPaymentState> = _merchantPaymentState.asStateFlow()

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

    fun sendPayment(amount:String,memo:String,toPhone:String,pin:String,qrIndicator:String){

        _isLoading.value = true
        viewModelScope.launch{
            val response = async {
                repository.sendPayment(amount,memo,toPhone,pin,qrIndicator)
            }
            println(response.await()?.responseCode + response.await()?.responseDescription)
            if (response.await()?.responseCode == "000"){
                _transactionN.value = response.await()?.trxReference.toString()
                _isLoading.value = false
                _merchantPaymentState.value = MerchantPaymentState.Success
            }else if (response.await()?.responseCode == "312"){
                _isLoading.value = false
                _merchantPaymentState.value = MerchantPaymentState.Error(SendMoneyErrorType.PIN)
            }else if(response.await()?.responseCode == "365"){
                _isLoading.value = false
                _merchantPaymentState.value = MerchantPaymentState.Error(SendMoneyErrorType.MINAMOUNT)
            }else if(response.await()?.responseCode == "367"){
                _isLoading.value = false
                _merchantPaymentState.value = MerchantPaymentState.Error(SendMoneyErrorType.MAXTRANSACTION)
            }else if (response.await()?.responseCode  == "373"){
                _isLoading.value = false
                _merchantPaymentState.value =  MerchantPaymentState.Error(SendMoneyErrorType.INVALIDPHONE)
            }else if( response.await()?.responseCode == "316"){
                _isLoading.value = false
                _merchantPaymentState.value =  MerchantPaymentState.Error(SendMoneyErrorType.ACCOUNTNOTFOUND)
            }
        }
    }

    sealed class MerchantPaymentState {
        object Idle : MerchantPaymentState()
        object  Success : MerchantPaymentState()
        object Loading : MerchantPaymentState()
        data class Error( val errorType: SendMoneyErrorType) : MerchantPaymentState()

    }

}