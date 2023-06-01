package com.example.s2m.viewmodel

import com.example.s2m.repository.ContactUsRepository
import com.example.s2m.util.ContactUsErrorType
import com.example.s2m.util.TransferErrorType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class ContactUsViewModel(private val repository: ContactUsRepository):ViewModel() {

        private val _subject : MutableStateFlow<String> = MutableStateFlow("")
        val subject :StateFlow<String> = _subject.asStateFlow()

        private val _email:MutableStateFlow<String> = MutableStateFlow("")
        val email : StateFlow<String> = _email.asStateFlow()

        private val _requestType : MutableStateFlow<Int> = MutableStateFlow(0)
        val requestType : StateFlow<Int> = _requestType.asStateFlow()

        private val _message : MutableStateFlow<String> = MutableStateFlow("")
        val message : StateFlow<String> = _message.asStateFlow()

        private val _fullName : MutableStateFlow<String> = MutableStateFlow("")
        val fullName : StateFlow<String> = _fullName.asStateFlow()

        private val _phoneNumber : MutableStateFlow<String> = MutableStateFlow("")
        val phoneNumber : StateFlow<String> = _phoneNumber.asStateFlow()

        private val _contactUsState:MutableStateFlow<ContactUsState> = MutableStateFlow(ContactUsState.Idle)


        fun onSubjectChanged(subject :String){
                _subject.value = subject
        }

        fun onEmailChanged(email:String){
                _email.value = email
        }

        fun onRequestTypeChanged(requestType:Int){
                _requestType.value = requestType
        }

        fun onMessageChanged(message:String){
                _message.value = message
        }

        fun onFullNameChanged(fullName:String){
                _fullName.value = fullName
        }

        fun onPhoneNumberChanged(phoneNumber:String){
                _phoneNumber.value = phoneNumber
        }

        fun clearState(){
                _message.value = ""
                _phoneNumber.value = ""
                _email.value= ""
                _subject.value=""
                _fullName.value=""
                _requestType.value = 0
                _contactUsState.value = ContactUsState.Idle
        }

        fun contactUs(subject:String,email:String,requestType:Int,message:String,fullName:String,phoneNumber:String):ContactUsState?{

                runBlocking {
                        val response = async {
                                repository.contactUs(subject,email,requestType,message,fullName,phoneNumber)
                        }

                        if (response.await()?.responseCode == "000"){
                                println(response.await()!!.responseCode+""+ response.await()!!.responseDescription)
                               _contactUsState.value = ContactUsState.Success
                        }else{
                               // _contactUsState.value = ContactUsState.Error
                                println(response.await()!!.responseCode+""+ response.await()!!.responseDescription)
                        }
                }

                return _contactUsState.value

        }

        sealed class ContactUsState {
                object Idle : ContactUsState()
                object  Success : ContactUsState()
                object Loading : ContactUsState()
                data class Error( val errorType: ContactUsErrorType) : ContactUsState()

        }

}