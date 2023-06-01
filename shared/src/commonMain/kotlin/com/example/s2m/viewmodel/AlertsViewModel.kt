package com.example.s2m.viewmodel


import com.example.s2m.model.Alert
import com.example.s2m.model.GetAlertResponse
import com.example.s2m.repository.AlertRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlertsViewModel(private val repository: AlertRepository):ViewModel() {


    private val _unreadNotificationCount = MutableStateFlow(0)
    val unreadNotificationCount: StateFlow<Int> = _unreadNotificationCount.asStateFlow()

    var currentPage = 1
    private val _alertResponse: MutableStateFlow<GetAlertResponse?> = MutableStateFlow(GetAlertResponse("","",null))
    val alertResponse: StateFlow<GetAlertResponse?> = _alertResponse.asStateFlow()



    private val _alert:MutableStateFlow<Alert?> = MutableStateFlow(Alert("",0,false,"","","","",""))
    val alert:StateFlow<Alert?> =_alert.asStateFlow()

    private val _isDetailScreenVisible :MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDetailScreenVisible: StateFlow<Boolean> = _isDetailScreenVisible.asStateFlow()






    fun clearState(){
        currentPage = 1
        _alertResponse.value = GetAlertResponse("","",null)

    }


    fun onChangeVisibility(switch:Boolean){
        _isDetailScreenVisible.value = switch

    }
    fun onAlertSelected(alert:Alert){
        _alert.value=alert
    }
    fun getUnreadedAlert(){

        runBlocking {
            val response=async {
                repository.getUnreadedAlerts()
            }
            if(response?.await()?.responseCode   == "000"){
                _unreadNotificationCount.value= response.await()?.unReadedAlert!!
            }
        }

    }

    fun getAlert() {
        viewModelScope.launch {
            val response = repository.getAlerts(currentPage)
            if (response?.responseCode == "000") {
                val existingAlertList = _alertResponse.value?.alertList ?: emptyList()
                val combinedAlertList = existingAlertList.toMutableList()
                response.alertList?.let { combinedAlertList.addAll(it) }
                _alertResponse.value = response.copy(alertList = combinedAlertList)
            }
        }
    }

    fun readAlert(alertCode: Int) {
        viewModelScope.launch {
            val response = repository.readAlert(alertCode)
            if (response?.responseCode == "000") {
                _alertResponse.value = _alertResponse.value?.copy(
                    alertList = _alertResponse.value?.alertList?.map { alert ->
                        if (alert.iden == alertCode) {
                            alert.copy(readed = true)
                        } else {
                            alert
                        }
                    }
                )
            }
        }
    }





    fun loadNextPage() {
        currentPage++
        getAlert()
    }
}