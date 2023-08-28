package com.example.s2m.android.view.merchantPaymentScreen

import LoadingAnimation
import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
import com.example.s2m.util.SendMoneyErrorType
import com.example.s2m.util.TransferErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.MerchantPaymentViewModel
import com.example.s2m.viewmodel.TransferViewModel
import com.example.s2m.viewmodel.WithdrawalViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MerchantPaymentScreen2(
    navController: NavController,
    merchantPaymentViewModel: MerchantPaymentViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel()
){
    val user: User by loginViewModel.user.collectAsState()
    var showDialog1 by remember { mutableStateOf(false) }
    var otpValue by remember{ mutableStateOf("") }
    val loading by merchantPaymentViewModel.isLoading.collectAsState()
    val merchantPaymentState by merchantPaymentViewModel.merchantPaymentState.collectAsState()

    LaunchedEffect(merchantPaymentState){
        when(merchantPaymentState){
            is MerchantPaymentViewModel.MerchantPaymentState.Success -> navController.navigate(Routes.Merchant3.name)
            is MerchantPaymentViewModel.MerchantPaymentState.Error -> {
                val errorType = (merchantPaymentState as MerchantPaymentViewModel.MerchantPaymentState.Error).errorType
                when (errorType) {
                    SendMoneyErrorType.PIN -> showDialog1 = true
                    SendMoneyErrorType.INVALIDPHONE -> {}
                    SendMoneyErrorType.ACCOUNTNOTFOUND -> {}
                    SendMoneyErrorType.MAXTRANSACTION -> {}
                    SendMoneyErrorType.MINAMOUNT -> {}
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
                            text = "Merchant Payment", modifier = Modifier
                                .weight(1f),
                            color= Color.White
                        ) }
                })
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
                            .size(70.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(Color(0xff1D267D))
                    )
                    Text(
                        text = "+212${merchantPaymentViewModel.toPhone.value}",
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
                    Text("Amount: ${merchantPaymentViewModel.amount.value} MAD", color = Color.Black)
                    Text("Memo: ${merchantPaymentViewModel.memo.value}", color = Color.Black)
                    Text("Fee: 5.00 MAD", color = Color.Black)
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
                                style = MaterialTheme.typography.h4,
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
                        merchantPaymentViewModel.onPinChanged(otpValue)
                        merchantPaymentViewModel.sendPayment(merchantPaymentViewModel.amount.value,merchantPaymentViewModel.memo.value,merchantPaymentViewModel.toPhone.value,
                            getHash(merchantPaymentViewModel.pin.value,),"NO")


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
        if(showDialog1){
            AlertDialog(
                modifier = Modifier
                    .height(150.dp)
                    .width(300.dp),
                backgroundColor = Color(backgroundColor),

                onDismissRequest = { showDialog1 = false },
                title = {
                    Text(text = "Invalid PIN!!",modifier= Modifier.padding(start=80.dp,top=20.dp),
                        fontWeight = FontWeight.Bold)
                },
                buttons = {
                    Column {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                                topBarColor
                            )
                            ),
                            shape = RoundedCornerShape(50),
                            onClick = {
                                showDialog1 = false
                            },
                            modifier = Modifier
                                .padding(start = 50.dp, top = 60.dp)
                                .width(200.dp)


                        ) {

                            Text(
                                text = "OK",
                                color = Color.White,
                            )
                        }
                    }
                }
            )
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
    }

}