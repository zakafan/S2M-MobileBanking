package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.service.quickaccesswallet.WalletCard
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
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

    alertsViewModel.getUnreadedAlert()
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







                            /* Column(
                                 modifier = Modifier.padding(start = 16.dp)
                             ) {
                                 Text(
                                     text = "Bonjour ${it.lastName}",
                                     fontSize = 25.sp,
                                     fontWeight = FontWeight.SemiBold,
                                     modifier = Modifier.padding(top = 30.dp)
                                 )
                                 Spacer(modifier = Modifier.height(7.dp))
                                 Text(
                                     text = "Balance : ${it.equipmentList[0].balance} MAD",
                                     fontSize = 18.sp,
                                     textAlign = TextAlign.Center,
                                     modifier = Modifier
                                         .fillMaxWidth()
                                         .padding(end = 80.dp)
                                 )
                             }*/

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


            AutoSlidingCarousel(
                itemsCount = cards.size,
                itemContent = { index ->
                    WalletCard(
                        balance = cards[index].balance,
                        currency = cards[index].currency,
                        cardNumber = cards[index].cardNumber,
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize()

                    )
                }
            )





       /* user.responseLogin?.let {
            val wallets = it.equipmentList
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),

        ) {
            AutoSlidingCarousel(
                itemsCount = wallets.size,
                itemContent = { index ->
                    WalletCard(
                        balance = wallets[index].balance,
                        currency = wallets[index].currencyAlphaCode,
                        cardNumber = "879",
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize()

                    )
                }
            )
        }}*/



        val context = LocalContext.current
        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(70.dp)
                .padding(top = 130.dp)
                .background(color = Color.Transparent)
                ,

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
                        CustomButton1(onClick = { navController.navigate(Routes.Transfer1.name)},text="Transfer",icon=R.drawable.transfer_money)
                        CustomButton1(onClick = { navController.navigate(Routes.Send1.name) },text="Money Send",icon=R.drawable.send_money)
                        CustomButton1(onClick = {navController.navigate(Routes.Withdrawal1.name) },text="Cash Out",icon=R.drawable.withdraw)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CustomButton1(onClick = { navController.navigate(Routes.History.name) },text=" History",icon=R.drawable.time_management)
                        CustomButton1(onClick = { },text="Wallet details",icon=R.drawable.card)
                        CustomButton1(onClick = { navController.navigate(Routes.Merchant1.name)},text="Pay Merchant",icon=R.drawable.payment)
                    }
                }
            }

        }

        }
    }

@Composable
fun WalletCard(balance: Double, currency: String, cardNumber: String,modifier:Modifier) {
    Card(
        backgroundColor = Color(0xff3F72AF),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(25.dp)
            .width(340.dp),
        elevation = 30.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Wallet Balance",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                text =   balance.toString() + "MAD",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                ,
                color = Color.White
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Currency",
                        style = MaterialTheme.typography.caption,
                        color = Color.White                    )
                    Text(
                        text = currency,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp),
                        color = Color.White
                    )
                }
                Column {
                    Text(
                        text = "Wallet Number",
                        style = MaterialTheme.typography.caption,
                        color = Color.White
                    )
                    Text(
                        text = cardNumber,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}



class CardData(val balance: Double, val currency: String, val cardNumber: String)

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = Color(backgroundColor).copy(alpha = if (isDragged) 0f else 0f))

    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)

        }

        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color(0xff3F72AF)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 9.dp
            )
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color =   Color.White ,
    unSelectedColor: Color =  Color.Gray ,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
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
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Text
        Text(
            text = user.responseLogin?.phone ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}



@Composable
fun CustomButton1(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: Int
) {
    Column(
        modifier = modifier
            .width(72.dp)
            .height(100.dp),
    ) {
        Button(
            shape = RoundedCornerShape(20),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff112D4E)),
            modifier = modifier
                .width(80.dp)
                .height(75.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Icon/Wallet",
                tint = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Visible
            )
        }
    }
}
