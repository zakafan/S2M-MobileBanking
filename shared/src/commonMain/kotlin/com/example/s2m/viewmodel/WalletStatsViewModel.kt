package com.example.s2m.viewmodel

import com.example.s2m.model.WalletStatsResponse
import com.example.s2m.repository.WalletStatsRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WalletStatsViewModel(private val repository: WalletStatsRepository): ViewModel() {

    private val _walletConsumptionList:MutableStateFlow<WalletStatsResponse> = MutableStateFlow(WalletStatsResponse("","",null))
    var walletConsumptionList:StateFlow<WalletStatsResponse> = _walletConsumptionList.asStateFlow()

    fun getWalletStats(){
        viewModelScope.launch {
            val response = async {
                repository.getWalletStatistics()
            }
            println(response.await()?.responseCode+response.await()?.responseDescription)
            if(response.await()?.responseCode == "000"){
                _walletConsumptionList.value.walletConsumption = response.await()?.walletConsumption
            }else{
                println("error")
            }
        }
    }
}