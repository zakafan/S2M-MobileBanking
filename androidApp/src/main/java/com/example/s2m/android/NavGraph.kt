package com.example.s2m.android

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.s2m.android.util.Routes
import com.example.s2m.android.view.*
import com.example.s2m.android.view.alertScreens.AlertsScreen
import com.example.s2m.android.view.alertScreens.AlertsScreen2
import com.example.s2m.android.view.beneficiaryScreen.AddBeneficiaryScreen
import com.example.s2m.android.view.beneficiaryScreen.BeneficiaryScreen
import com.example.s2m.android.view.beneficiaryScreen.RecapAddBeneficiaryScreen
import com.example.s2m.android.view.merchantPaymentScreen.*
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen1
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen2
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen3
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen4
import com.example.s2m.android.view.settingsScreens.ChangePasswordPin
import com.example.s2m.android.view.transferScreen.TransferScreen1
import com.example.s2m.android.view.transferScreen.TransferScreen2
import com.example.s2m.android.view.transferScreen.TransferScreen3
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen1
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen2
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen3
import com.example.s2m.repository.*
import com.example.s2m.viewmodel.*


private val loginViewModel     = LoginViewModel(repository = LoginRepository())
private val forexViewModel     = ForexViewModel(repository = ForexRepository())
private val contactUsViewModel = ContactUsViewModel(repository = ContactUsRepository())
private val sendMoneyViewModel = SendMoneyViewModel(repository = SendMoneyRepository(loginViewModel))
private val transferViewModel  = TransferViewModel(repository = TransferRepository(loginViewModel))
private val alertsViewModel    = AlertsViewModel(repository = AlertRepository(loginViewModel))
private val logoutViewModel    = LogoutViewModel(repository = LogoutRepository(loginViewModel))
private val beneficiaryViewModel = BeneficiaryViewModel(repository = AddBeneficiaryRepository(loginViewModel))
private val profileViewModel    = ProfileViewModel(repository = ProfileRepository(loginViewModel))
private val withdrawalViewModel = WithdrawalViewModel(repository = WithdrawalRepository(loginViewModel))
private val merchantPaymentViewModel = MerchantPaymentViewModel(repository = MerchantPaymentRepository(loginViewModel))
private val walletStatsViewModel = WalletStatsViewModel(repository = WalletStatsRepository(loginViewModel))


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    callback: LocationPermissionCallback,
) {
    val alert by alertsViewModel.alert.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.name
    ) {
        composable(route = Routes.Login.name) {
            LoginScreen(loginViewModel = loginViewModel, navController = navController, forexViewModel = forexViewModel , callback =callback )
        }
        composable(route = Routes.Welcome.name) {
            WelcomeScreen(loginViewModel = loginViewModel, navController = navController, alertsViewModel, logoutViewModel = logoutViewModel, withdrawalViewModel = withdrawalViewModel,
                merchantPaymentViewModel = merchantPaymentViewModel)
        }
        composable(route = Routes.Beneficiary.name) {
            BeneficiaryScreen(loginViewModel = loginViewModel, navController = navController,logoutViewModel= logoutViewModel, beneficiaryViewModel = beneficiaryViewModel)
        }
        composable(route = Routes.AddBeneficiary.name) {
            AddBeneficiaryScreen( navController = navController, addBeneficiaryViewModel = beneficiaryViewModel )
        }
        composable(route = Routes.ValidBenef.name) {
            RecapAddBeneficiaryScreen( navController = navController, beneficiaryViewModel = beneficiaryViewModel )
        }
        composable(route=Routes.Transfer1.name){
            TransferScreen1(navController = navController, loginViewModel = loginViewModel, transferViewModel = transferViewModel, logoutViewModel = logoutViewModel)
        }
        composable(route=Routes.Transfer2.name){
            TransferScreen2(navController = navController ,transferViewModel = transferViewModel,loginViewModel = loginViewModel, logoutViewModel = logoutViewModel)
        }
        composable(route=Routes.Transfer3.name){
            TransferScreen3(navController = navController ,transferViewModel = transferViewModel,loginViewModel = loginViewModel, logoutViewModel = logoutViewModel)
        }
        composable(route=Routes.Forex.name){
            ForexScreen(navController = navController, forexViewModel = forexViewModel)
        }
        composable(route= Routes.Contact.name){
            ContactUsScreen(navController = navController, contactUsViewModel = contactUsViewModel)
        }
        composable(route= Routes.Send1.name){
            SendMoneyScreen1(navController = navController, loginViewModel = loginViewModel, sendMoneyViewModel = sendMoneyViewModel, logoutViewModel = logoutViewModel)
        }
        composable(route= Routes.Send2.name){
            SendMoneyScreen2(navController = navController, loginViewModel = loginViewModel, sendMoneyViewModel = sendMoneyViewModel,
                logoutViewModel= logoutViewModel)
        }
        composable(route= Routes.Send3.name){
            SendMoneyScreen3(navController = navController, loginViewModel = loginViewModel, sendMoneyViewModel = sendMoneyViewModel,
                logoutViewModel= logoutViewModel)
        }
        composable(route= Routes.Send4.name){
            SendMoneyScreen4(navController = navController, loginViewModel = loginViewModel, sendMoneyViewModel = sendMoneyViewModel,
                logoutViewModel= logoutViewModel)
        }
        composable(route= Routes.Alerts.name){
            AlertsScreen(navController = navController, loginViewModel = loginViewModel, alertsViewModel = alertsViewModel, logoutViewModel = logoutViewModel)
        }
        composable("alert") {
            alert?.let { it1 -> AlertsScreen2(it1,alertsViewModel=alertsViewModel, logoutViewModel = logoutViewModel, loginViewModel = loginViewModel, navController = navController) }
        }
        composable(route= Routes.History.name){
            HistoryTransactionsScreen(navController = navController, loginViewModel = loginViewModel, logoutViewModel = logoutViewModel, walletStatsViewModel = walletStatsViewModel)
        }
        composable(route= Routes.Locations.name){
            LocationsScreen(navController = navController)
        }
        composable(route= Routes.Profile.name){
            ProfileScreen(navController = navController, profileViewModel = profileViewModel, loginViewModel = loginViewModel )
        }
        composable(route= Routes.ChangePassword.name){
            ChangePasswordPin(navController = navController, profileViewModel = profileViewModel )
        }
        composable(route= Routes.Withdrawal1.name){
            WithdrawalScreen1(navController = navController, withdrawalViewModel = withdrawalViewModel, loginViewModel = loginViewModel  )
        }
        composable(route= Routes.Withdrawal2.name){
            WithdrawalScreen2(navController = navController, withdrawalViewModel = withdrawalViewModel, loginViewModel = loginViewModel  )
        }
        composable(route= Routes.Withdrawal3.name){
            WithdrawalScreen3(navController = navController, withdrawalViewModel = withdrawalViewModel, loginViewModel = loginViewModel  )
        }
        composable(route= Routes.Merchant1.name){
            MerchantPaymentScreen1(navController = navController, merchantPaymentViewModel = merchantPaymentViewModel, loginViewModel = loginViewModel  )
        }
        composable(route= Routes.Merchant2.name){
            MerchantPaymentScreen2(navController = navController, merchantPaymentViewModel = merchantPaymentViewModel, loginViewModel = loginViewModel  )
        }
        composable(route= Routes.Merchant3.name){
            MerchantPaymentScreen3(navController = navController, merchantPaymentViewModel = merchantPaymentViewModel, loginViewModel = loginViewModel  )
        }
        composable(route = Routes.Merchant.name){
            MerchantPaymentScreen(navController = navController)
        }
        composable(route = Routes.MerchantScanner.name){
            Scanner(navController = navController, merchantPaymentViewModel = merchantPaymentViewModel)
        }
        composable(route = Routes.Withdrawal.name){
            WithdrawalScreen(navController = navController)
        }
        composable(route = Routes.WithdrawalScanner.name){
            com.example.s2m.android.view.withdrawalScreen.Scanner(navController = navController,withdrawalViewModel=withdrawalViewModel)
        }



    }
}
fun NavGraphBuilder.beneficiaryGraph(navController:NavController){
    navigation(startDestination = Routes.Beneficiary.name,route = Routes.Beneficiary.name){
        composable(route = Routes.Beneficiary.name){
            BeneficiaryScreen(loginViewModel= loginViewModel,navController=navController)
        }
    }
}

fun NavGraphBuilder.transferGraph(navController: NavController){
    navigation(startDestination = "transfer1",route="test"){
        composable(route = "transfer1"){
            TransferScreen1(navController = navController, loginViewModel = loginViewModel, transferViewModel = transferViewModel )
        }
        composable(route="screen 2 "){

        }
    }
}