package com.example.s2m.android

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.s2m.android.util.Routes
import com.example.s2m.android.view.*
import com.example.s2m.android.view.alertScreens.AlertsScreen
import com.example.s2m.android.view.alertScreens.AlertsScreen2
import com.example.s2m.android.view.beneficiaryScreen.AddBeneficiaryScreen
import com.example.s2m.android.view.beneficiaryScreen.BeneficiaryScreen
import com.example.s2m.android.view.beneficiaryScreen.RecapAddBeneficiaryScreen
import com.example.s2m.android.view.merchantPaymentScreen.MerchantPaymentScreen1
import com.example.s2m.android.view.merchantPaymentScreen.MerchantPaymentScreen2
import com.example.s2m.android.view.merchantPaymentScreen.MerchantPaymentScreen3
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen1
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen2
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen3
import com.example.s2m.android.view.sendMoneyScreen.SendMoneyScreen4
import com.example.s2m.android.view.settingsScreens.ChangePasswordPin
import com.example.s2m.android.view.transferScreen.TransferScreen1
import com.example.s2m.android.view.transferScreen.TransferScreen2
import com.example.s2m.android.view.transferScreen.TransferScreen3
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen1
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen2
import com.example.s2m.android.view.withdrawalScreen.WithdrawalScreen3
import com.example.s2m.repository.*
import com.example.s2m.viewmodel.*
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MainActivity : ComponentActivity(),LocationPermissionCallback {

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            disableSSLCertificateValidation()

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val alert by alertsViewModel.alert.collectAsState()
                   // val locationPermissionCallback = this@MainActivity
                    val navController = rememberNavController( )
                    SetupNavGraph(navController = navController, callback = this@MainActivity)
                    NavHost(navController = navController, startDestination = Routes.Login.name ){
                        composable(route = Routes.Login.name) {
                            LoginScreen(loginViewModel = loginViewModel, navController = navController, forexViewModel = forexViewModel, callback =this@MainActivity)
                        }
                        composable(route = Routes.Welcome.name) {
                            WelcomeScreen(loginViewModel = loginViewModel, navController = navController, alertsViewModel, logoutViewModel = logoutViewModel)
                        }
                        composable(route = Routes.Beneficiary.name) {
                            BeneficiaryScreen(loginViewModel = loginViewModel, navController = navController,logoutViewModel= logoutViewModel,beneficiaryViewModel=beneficiaryViewModel)
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
                            HistoryTransactionsScreen(navController = navController, loginViewModel = loginViewModel, logoutViewModel = logoutViewModel)
                        }
                        composable(route= Routes.Locations.name){
                            LocationsScreen(navController = navController )
                        }
                        composable(route= Routes.Profile.name){
                            ProfileScreen(navController = navController, profileViewModel = profileViewModel ,loginViewModel= loginViewModel)
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

                    }
                }
            }
        }
        requestLocationPermissions()
    }

    private val locationPermissionRequestCode = 100

    override  fun requestLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, locationPermissionRequestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permissions granted. You can proceed with map-related functionality.
            } else {
                // Location permissions denied. Handle accordingly.
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (loginViewModel.isWelcomeScreenVisible) {
            // do nothing, user can't exit from login screen
        } else {
            super.onBackPressed()
        }
    }

}

fun disableSSLCertificateValidation() {
    HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
        }
    )
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
}

private const val key = "MOBILE@PTF_S2M#T@dAwul!?*"
private const val HMAC_SHA512 = "HmacSHA512"

fun getHash(data: String): String {
    val secretKeySpec = SecretKeySpec(key.toByteArray(), HMAC_SHA512)
    val mac = try {
        Mac.getInstance(HMAC_SHA512).apply {
            init(secretKeySpec)
        }
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        return ""
    } catch (e: InvalidKeyException) {
        e.printStackTrace()
        return ""
    }
    val msgByte = data.toByteArray()
    val messageCrypte = mac.doFinal(msgByte)
    return Base64.encodeToString(messageCrypte, Base64.NO_WRAP)
}


fun getAppContext(context: Context): Context {
    return context.applicationContext
}

interface LocationPermissionCallback {
    fun requestLocationPermissions()
}




