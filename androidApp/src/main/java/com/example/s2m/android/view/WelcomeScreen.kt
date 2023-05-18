package com.example.s2m.android.view

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.android.R
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController

) {


    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val contextForToast = LocalContext.current.applicationContext



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
        ?.let { WalletCard(balance = it.balance, currency = it.currencyAlphaCode, cardNumber = "54",Modifier.fillMaxWidth()) }


//val op = Open(scaffoldState = scaffoldState)
    
    Scaffold(

        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFC4C4C4),
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {

                TopAppBar(
                    backgroundColor = Color(0xFF2A0000),
                    modifier = Modifier.height(150.dp),
                    title = {
                        user.responseLogin?.let {
                            Row(
                                modifier =Modifier.padding(bottom = 55.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color(0xff00E0F7))
                                }
                                Column(
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
                                }
                            }
                        }
                    }
                )




            }
        },
        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController)
        },
        bottomBar = {
            BottomNav(navController = navController,"welcome")
        }
    ) {
        val cards = listOf(
            CardData(balance = 1000.0, currency = "USD", cardNumber = "**** **** **** 1234"),
            CardData(balance = 500.0, currency = "EUR", cardNumber = "**** **** **** 5678"),
            CardData(balance = 750.0, currency = "GBP", cardNumber = "**** **** **** 9012")
            // Add more card data as needed
        )

        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),

        ) {
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
        }
       // CardSlider()
            /*user.responseLogin?.equipmentList?.get(0).let {
                Spacer(modifier = Modifier.height(20.dp))
                if (it != null) {
                    WalletCard(
                        balance = it.balance,
                        currency = it.currencyAlphaCode,
                        cardNumber = "54",

                        )
                }
            }*/



        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(70.dp)
                .padding(top=130.dp)
                .background(color = Color.Transparent)
                ,

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.width(1500.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CustomButton1(onClick = { },text="Transfer")
                        CustomButton1(onClick = { },text="Transfer")
                        CustomButton1(onClick = { },text="Transfer")
                        CustomButton1(onClick = { },text="Transfer")
                        
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CustomButton1(onClick = { },text="Transfer")
                        CustomButton1(onClick = { },text="Transfer")
                        CustomButton1(onClick = { },text="Transfer")

                    }
                }
            }

        }

        }
    }

@Composable
fun WalletCard(balance: Double, currency: String, cardNumber: String,modifier:Modifier) {
    Card(
        backgroundColor = Color(0xFF00AAD4),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(25.dp)
            .width(340.dp)
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
        .background(color = Color(0xFF2A0000)),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
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

@Composable
fun CustomButton1(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Column(
        modifier = modifier
            .width(72.dp)
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            shape = RoundedCornerShape(20),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff2a0000)),
            modifier = modifier
                .width(72.dp)
                .height(78.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.forex),
                contentDescription = "Icon/Wallet",
                tint = Color(0xff00e0f7)
            )
        }
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            color=Color.Black
        )
    }
}

