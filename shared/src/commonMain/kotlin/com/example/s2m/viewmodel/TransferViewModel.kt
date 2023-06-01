package com.example.s2m.viewmodel

import com.example.s2m.model.User
import com.example.s2m.repository.TransferRepository
import com.example.s2m.util.TransferErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class TransferViewModel(private val repository: TransferRepository):ViewModel() {

    private val _amount: MutableStateFlow<String> = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _memo: MutableStateFlow<String> = MutableStateFlow("")
    val memo: StateFlow<String> = _memo.asStateFlow()

    private val _toPhone: MutableStateFlow<String> = MutableStateFlow("")
    val toPhone: StateFlow<String> = _toPhone.asStateFlow()

    private val _pin: MutableStateFlow<String> = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _beneficiaryName:MutableStateFlow<String> = MutableStateFlow("")
    val beneficiaryName:StateFlow<String> = _beneficiaryName.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _transferState: MutableStateFlow<TransferState> = MutableStateFlow(
        TransferState.Idle
    )
    val transferState: StateFlow<TransferState> = _transferState.asStateFlow()


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

    fun clearState() {
        _amount.value = ""
        _toPhone.value = ""
        _memo.value= ""
        _pin.value=""
        _beneficiaryName.value=""
        _transferState.value=TransferState.Idle
    }
    fun transfer(amount:String,memo:String,toPhone:String,pin:String): TransferState {

        runBlocking {
            val response = async {
                repository.transfer(amount,memo,toPhone,pin)

            }
            println(response.await()?.responseDescription+"and"+response.await()?.responseCode)
            if (response.await()?.responseCode=="000"){
               /* _amount.value=amount
                _memo.value=memo
                _toPhone.value=toPhone
                _pin.value=pin*/
                _transferState.value= TransferState.Success
                println("transfer daz a 3chiri")
            }else if(response.await()?.responseCode=="312"){
                _transferState.value= TransferState.Error(TransferErrorType.PIN)
            }else if(response.await()?.responseCode=="365"){
                _transferState.value= TransferState.Error(TransferErrorType.MinAmount)
            }else if(response.await()?.responseCode=="367"){
                _transferState.value= TransferState.Error(TransferErrorType.MaxTransactions)
            }
        }

        println(_transferState.value)
        return _transferState.value
    }

    sealed class TransferState {
        object Idle : TransferState()
        object  Success : TransferState()
        object Loading : TransferState()
        data class Error( val errorType: TransferErrorType) : TransferState()

    }
}