package com.example.s2m.android.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.util.*
import com.example.s2m.model.User
import com.example.s2m.viewmodel.AlertsViewModel
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun WelcomeScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController,
    alertsViewModel: AlertsViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel()

) {

    //val read = alertsViewModel.getUnreadedAlert()
    val unreadNotificationCount by alertsViewModel.unreadNotificationCount.collectAsState()

    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            // This will run when the composable is removed from the composition
            loginViewModel.isWelcomeScreenVisible = false
        }
    }
    LaunchedEffect(Unit) {
        // This will run when the composable is first displayed
        loginViewModel.isWelcomeScreenVisible = true
    }

    //user.responseLogin?.equipmentList?.get(0)
      //  ?.let { WalletCard(balance = it.balance, currency = it.currencyAlphaCode, cardNumber = "54",Modifier.fillMaxWidth()) }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(backgroundColor),
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {
                TopAppBar(
                    backgroundColor = Color(topBarColor),
                    modifier = Modifier.height(150.dp),
                    title = {
                        user.responseLogin?.let {

                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier.align(Alignment.TopCenter)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(bottom = 55.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    scaffoldState.drawerState.open()
                                                }
                                            },
                                            modifier = Modifier.padding(bottom = 50.dp)
                                        ) {
                                            Icon(
                                                Icons.Filled.Menu,
                                                contentDescription = "Menu",
                                                tint = Color.White,
                                                modifier = Modifier.padding(end = 25.dp)
                                            )
                                        }

                                        Text(
                                            text = "Bonjour ${it.firstName}", modifier = Modifier
                                                .weight(1f)
                                                .padding(bottom = 50.dp),
                                            color=Color.White

                                        )

                                        Box(
                                            modifier = Modifier.padding(end = 1.dp, top = 12.dp)
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    alertsViewModel.getAlert()
                                                    navController.navigate(Routes.Alerts.name)
                                                },
                                                modifier = Modifier.padding(bottom = 60.dp)
                                            ) {
                                                Icon(
                                                    Icons.Filled.Notifications,
                                                    contentDescription = "notification",
                                                    tint = Color.White
                                                )
                                            }

                                            BadgedBox(
                                                badge = {
                                                    val displayedCount = if (unreadNotificationCount > 99) "+99" else unreadNotificationCount.toString()
                                                    Badge { Text(displayedCount) }
                                                }
                                            ) {
                                                // This is an empty composable that acts as a placeholder for the badge
                                            }
                                        }


                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Balance: ${it.equipmentList[0].balance} MAD", color = Color.White,modifier=Modifier.padding(top=80.dp,end=70.dp), fontSize = 25.sp, fontStyle = FontStyle.Italic)
                                }
                            }
                        }
                    }
                )
            }
        },
        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController, logoutViewModel =logoutViewModel )
        },
        bottomBar = {
            BottomNav(navController = navController,"welcome")
        }
    ) {
        val cards = listOf(
            CardData(balance = 1000.0, currency = "USD", cardNumber = "**** **** **** 1234"),
            CardData(balance = 20000.0, currency = "USD", cardNumber = "**** **** **** 1234"),
            CardData(balance = 547.0, currency = "USD", cardNumber = "**** **** **** 1234")
            )
        user.responseLogin?.let {
            val wallets = it.equipmentList

            AutoSlidingCarousel(
                itemsCount = cards.size,
                itemContent = { index ->
                    WalletCard(
                        balance = cards[index].balance,
                        currency = cards[index].currency,
                        cardNumber = "879",
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize()

                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(70.dp)
                .padding(top = 130.dp)
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.width(2000.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CustomButton1(onClick = { navController.navigate("Transfer1")},text="Transfer",icon=R.drawable.transfer_money)
                        CustomButton1(onClick = { navController.navigate(Routes.Send1.name) },text="Money Send",icon=R.drawable.send_money)
                        CustomButton1(onClick = {navController.navigate(Routes.Withdrawal1.name) },text="Cash Out",icon=R.drawable.withdraw)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CustomButton1(onClick = { navController.navigate(Routes.History.name) },text=" History",icon=R.drawable.time_management)
                        CustomButton1(onClick = {navController.navigate(Routes.Withdrawal3.name) },text="Wallet details",icon=R.drawable.card)
                        CustomButton1(onClick = { navController.navigate(Routes.Merchant1.name)},text="Pay Merchant",icon=R.drawable.payment)
                    }
                }
            }
        }
        }
    }



















