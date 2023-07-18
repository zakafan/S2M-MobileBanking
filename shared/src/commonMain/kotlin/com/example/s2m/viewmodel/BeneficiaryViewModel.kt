package com.example.s2m.viewmodel

import com.example.s2m.repository.AddBeneficiaryRepository
import com.example.s2m.util.AddBeneficiaryErrorType
import com.example.s2m.util.TransferErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class BeneficiaryViewModel(private val repository: AddBeneficiaryRepository) :ViewModel(){

    private val _beneficiaryName: MutableStateFlow<String> = MutableStateFlow("")
    val beneficiaryName: StateFlow<String> = _beneficiaryName.asStateFlow()

    private val _phone: MutableStateFlow<String> = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _addBeneficiaryState: MutableStateFlow<AddBeneficiaryState> = MutableStateFlow(AddBeneficiaryState.Idle)
    val addBeneficiaryState: StateFlow<AddBeneficiaryState> = _addBeneficiaryState.asStateFlow()

    fun onBeneficiaryNameChanged(name:String){
        _beneficiaryName.value = name
    }
    fun onPhoneChanged(phone:String){
        _phone.value = phone
    }

    fun addBeneficiary(name:String,phone:String):AddBeneficiaryState{

        runBlocking{
            val response = async {
                repository.addBeneficiary(name,phone)
            }

            println(response.await()?.responseCode)
            if(response.await()?.responseCode == "000"){
                _addBeneficiaryState.value = AddBeneficiaryState.Success
            }else if(response.await()?.responseCode == "373"){
                _addBeneficiaryState.value = AddBeneficiaryState.Error(AddBeneficiaryErrorType.InvalidPhone)
            }
        }
        return _addBeneficiaryState.value
    }

    fun suspendBeneficiary(phone:String):AddBeneficiaryState{
        runBlocking {
            val response = async {
                repository.suspendBeneficiary(phone)
            }
            println(response.await()?.responseCode + response.await()?.responseDescription)
            if(response.await()?.responseCode == "000"){
                _addBeneficiaryState.value = AddBeneficiaryState.Success
            }
        }
        return _addBeneficiaryState.value
    }
    fun clearState(){
        _beneficiaryName.value= ""
        _phone.value= ""
        _addBeneficiaryState.value = AddBeneficiaryState.Idle
    }

    sealed class AddBeneficiaryState {
        object Idle : AddBeneficiaryState()
        object  Success : AddBeneficiaryState()
        object Loading : AddBeneficiaryState()
        data class Error( val errorType: AddBeneficiaryErrorType) : AddBeneficiaryState()

    }
}