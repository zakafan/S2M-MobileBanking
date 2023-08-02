package com.example.s2m.android.view.transferScreen

import LoadingAnimation
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.getHash
import com.example.s2m.android.util.*
import com.example.s2m.model.User
import com.example.s2m.util.TransferErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.TransferViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransferScreen2(
    transferViewModel: TransferViewModel = viewModel(),
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel()
){

    val user: User by loginViewModel.user.collectAsState()
    val transferState by transferViewModel.transferState.collectAsState()
    var showDialog1 by remember { mutableStateOf(false) }
    var otpValue by remember{ mutableStateOf("") }
    val loading by transferViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(transferState){
        when(transferState){
            is TransferViewModel.TransferState.Success -> navController.navigate(Routes.Transfer3.name)
            is TransferViewModel.TransferState.Error -> {
                val errorType = (transferState as TransferViewModel.TransferState.Error).errorType
                when (errorType) {
                    TransferErrorType.PIN -> showDialog1 = true

                    else -> {}
                }
            }
            else -> {}
        }
    }

    Scaffold(

        backgroundColor = Color(backgroundColor),
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xff112D4E),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {navController.popBackStack()

                            },

                            ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Menu",
                                tint = Color.White,
                            ) }

                        Text(
                            text = "Transfer", modifier = Modifier
                                .weight(1f),
                            color=Color.White
                        ) }
                })
        },

        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController, logoutViewModel =logoutViewModel )
        },
        bottomBar = {
            BottomNav(navController = navController,"beneficiary")
        }
    ){


       Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
            ) {
            Spacer(modifier = Modifier.height(1.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "From",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color= Color.Black,
                )
            }
            user.responseLogin?.let {
                val wallets = it.equipmentList

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
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "To",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }
           Box(
               modifier = Modifier
                   .width(500.dp)
                   .height(150.dp)
                   .background(color = Color(0xffAFD3E2), shape = RoundedCornerShape(20.dp)),
               contentAlignment = Alignment.Center,
           ) {
               Column(
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center,
               ) {
                   Image(
                       painter = painterResource(R.drawable.user),
                       contentDescription = "Person Image",
                       modifier = Modifier
                           .size(50.dp)
                           .clip(CircleShape),
                       colorFilter = ColorFilter.tint(Color(0xff1D267D))
                   )
                   Text(
                       text = transferViewModel.beneficiaryName.value,
                       fontSize = 24.sp,
                       fontWeight = FontWeight.Bold,
                       color = Color.Black,
                       modifier = Modifier.padding(top = 8.dp)
                   )
                   Text(
                       text = transferViewModel.toPhone.value,
                       fontSize = 16.sp,
                       color = Color.Black
                       ,
                       modifier = Modifier.padding(top = 4.dp)
                   )
               }
           }
           Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xffAFD3E2),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Amount: ${transferViewModel.amount.value} MAD", color = Color.Black)
                    Text("Memo: ${transferViewModel.memo.value}", color = Color.Black)
                    Text("Fee: 12.00 MAD", color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                    text = "Please Enter Your PIN Code",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color= Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value =otpValue,
                onValueChange ={
                    if(it.length<=4){
                        otpValue=it
                    }
                               },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center){
                        repeat(4){index->
                            val char=when{
                                index >=otpValue.length ->""
                                else ->otpValue[index].toString()
                            }
                            val isFocused=otpValue.length==index
                            Text(
                                modifier = Modifier
                                    .width(40.dp)
                                    .border(
                                        if (isFocused) 4.dp
                                        else 3.dp,
                                        if (isFocused) Color(0xffAFD3E2)
                                        else Color.Black,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                text=char,
                                style =MaterialTheme.typography.h4,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(60.dp))

                        }
                    }
                }
            )
           Spacer(modifier = Modifier.height(16.dp))
           Box(
               modifier = Modifier
                   .width(500.dp)
                   .height(120.dp),
               contentAlignment = Alignment.TopCenter,
           ){
               Button(
                   colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                   shape = RoundedCornerShape(50),
                   onClick = {
                       transferViewModel.onPinChanged(otpValue)
                       transferViewModel.transfer(transferViewModel.amount.value,transferViewModel.memo.value,transferViewModel.toPhone.value,
                           getHash(transferViewModel.pin.value))
                   },
                   modifier = Modifier
                       .width(500.dp)
                       .height(45.dp)) {
                   Text(
                       text = "Confirm",
                       fontSize = 20.sp,
                       color = Color.White,
                   )
               }
           }
        }
        if (loading) {
            AlertDialog(
                backgroundColor = Color.Transparent,
                onDismissRequest = { },
                properties = DialogProperties(dismissOnClickOutside = false),
                buttons = {},
                title = { },
                text = {
                    LoadingAnimation(isLoading = loading)
                }
            )
        }
        if(showDialog1){
            MyAlertDialog(text = "Invalid PIN!!", show = showDialog1) {
                showDialog1 = false
            }
        }
    }

}
