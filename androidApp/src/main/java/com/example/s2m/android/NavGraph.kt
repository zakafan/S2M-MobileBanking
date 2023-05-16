package com.example.s2m.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.s2m.android.util.Routes
import com.example.s2m.android.view.BeneficiaryScreen
import com.example.s2m.android.view.LoginScreen
import com.example.s2m.android.view.WelcomeScreen
import com.example.s2m.repository.LoginRepository
import com.example.s2m.viewmodel.LoginViewModel


private val loginViewModel = LoginViewModel(repository = LoginRepository())
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Routes.login.name
    ) {
        composable(route = Routes.login.name) {
            LoginScreen(loginViewModel = loginViewModel, navController = navController)
        }
        composable(route = Routes.welcome.name) {
            WelcomeScreen(loginViewModel = loginViewModel, navController = navController)
        }
        composable(route = Routes.beneficiary.name) {
            BeneficiaryScreen(loginViewModel = loginViewModel, navController = navController)

        }

    }
}
fun NavGraphBuilder.beneficiaryGraph(navController:NavController){
    navigation(startDestination = Routes.beneficiary.name,route = Routes.beneficiary.name){
        composable(route = Routes.beneficiary.name){
            BeneficiaryScreen(loginViewModel= loginViewModel,navController=navController)
        }
    }
}

fun NavGraphBuilder.transferGraph(navController: NavController){
    navigation(startDestination = "transfer",route="transfer"){
        composable(route = "transfer"){

        }
        composable(route="screen 2 "){

        }
    }
}