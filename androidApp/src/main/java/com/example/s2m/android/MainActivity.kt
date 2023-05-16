package com.example.s2m.android

import android.content.Context
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.s2m.android.util.Routes

import com.example.s2m.android.view.BeneficiaryScreen
import com.example.s2m.android.view.LoginScreen
import com.example.s2m.android.view.WelcomeScreen
import com.example.s2m.repository.LoginRepository
import com.example.s2m.repository.TransferRepository
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.TransferViewModel
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

class MainActivity : ComponentActivity() {

    private val loginViewModel = LoginViewModel(repository = LoginRepository())
    private val transferViewModel = TransferViewModel(repository = TransferRepository(loginViewModel=loginViewModel))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            disableSSLCertificateValidation()

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController( )
                    SetupNavGraph(navController = navController)
                    NavHost(navController = navController, startDestination = Routes.login.name ){
                        composable(Routes.login.name){
                            LoginScreen(loginViewModel = loginViewModel,navController)
                        }
                        composable(Routes.welcome.name){
                            WelcomeScreen(loginViewModel=loginViewModel,navController)
                        }
                        composable(Routes.beneficiary.name){
                            BeneficiaryScreen(loginViewModel = loginViewModel,navController)
                        }
                    }
                }
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



