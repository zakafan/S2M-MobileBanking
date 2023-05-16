package com.example.s2m.android.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bankingapp.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.android.R
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController

) {

    val login: String by loginViewModel.login.collectAsState()
    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()



    DisposableEffect(Unit) {
        onDispose {
            // This will run when the composable is removed from the composition
            loginViewModel.isWelcomeScreenVisible = false
        }
    }

    // Rest of the composable code

    LaunchedEffect(Unit) {
        // This will run when the composable is first displayed
        loginViewModel.isWelcomeScreenVisible = true
    }

    user.responseLogin?.equipmentList?.get(0)
        ?.let { WalletCard(balance = it.balance, currency = it.currencyAlphaCode, cardNumber = "54") }

//val op = Open(scaffoldState = scaffoldState)
    
    Scaffold(

        backgroundColor = Color.LightGray,
        topBar = {
            TopAppBar(
                title = { Text(text = "S2M") },
                
                navigationIcon = {
                    IconButton(onClick = { } ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController)
        },
        bottomBar = {
            BottomNav(navController = navController,"welcome")
        }
    ) {

            user.responseLogin?.equipmentList?.get(0).let {
                Spacer(modifier = Modifier.height(20.dp))
                if (it != null) {
                    WalletCard(
                        balance = it.balance,
                        currency = it.currencyAlphaCode,
                        cardNumber = "54",

                        )
                }
            }


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            user.responseLogin?.let {
                Text(text = it.lastName, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            }

        }

        }
    }


/*@Composable
fun WelcomeScreen(loginViewModel: LoginViewModel = viewModel(), navController: NavController) {

    val login: String by loginViewModel.login.collectAsState()
    val user: User by loginViewModel.user.collectAsState()


    user.responseLogin?.equipmentList?.get(0)
        ?.let { WalletCard(balance = it.balance, currency = it.currencyAlphaCode, cardNumber = "54") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        user.responseLogin?.let { Text(text = it.lastName, fontSize = 24.sp, fontWeight = FontWeight.Bold) }
        Button(
            onClick = {
                loginViewModel.clearState()
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "Logout")
        }
    }
}*/

@Composable
fun WalletCard(balance: Double, currency: String, cardNumber: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Wallet Balance",
                style = MaterialTheme.typography.caption
            )
            Text(
                text =   balance.toString() + "MAD",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Currency",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = currency,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Column {
                    Text(
                        text = "Card Number",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = cardNumber,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun TopAppBarWithSlideMenu(
    title: String,
    onSlideMenuClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onSlideMenuClick) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = actions,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 8.dp
    )
}



@Composable
fun UserInfoDrawer(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User Info Placeholder Image

        Image(
            painterResource(id = R.drawable.s2m_logo),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),

        )

        Spacer(modifier = Modifier.height(14.dp))

        // Login Text
        Text(
            text = user.responseLogin?.lastName ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Text
        Text(
            text = user.responseLogin?.phone ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}



