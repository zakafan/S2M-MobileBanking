package com.example.s2m.android.view.merchantPaymentScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.*
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.MerchantPaymentViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MerchantPaymentScreen1(
    navController: NavController,
    merchantPaymentViewModel: MerchantPaymentViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel()
){
    val amount:String by merchantPaymentViewModel.amount.collectAsState()
    val memo:String by merchantPaymentViewModel.memo.collectAsState()
    val toPhone:String by merchantPaymentViewModel.toPhone.collectAsState()
    val user    : User by loginViewModel.user.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

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
                                contentDescription = "Back",
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
                .fillMaxSize()
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
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
                ),
                value = toPhone,
                onValueChange = {  if (it.length <= 9)merchantPaymentViewModel.ontoPhoneChanged(it)},
                label = { Text("Beneficiary Phone*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
                ),
                value = amount,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it != ',' }
                    merchantPaymentViewModel.onAmountChanged(filteredValue)
                },
                label = { Text("Amount*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
                ),
                value = memo,
                onValueChange = { merchantPaymentViewModel.onMemoChanged(it)},
                label = { Text("Memo*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color =  Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            Box(
                modifier= Modifier.height(120.dp)
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        if( toPhone ==""|| amount=="" || memo==""){
                            showDialog = true

                        }else{
                            navController.navigate(Routes.Merchant2.name)
                        }

                    },
                    modifier = Modifier
                        .width(500.dp)
                        .height(45.dp)
                ) {
                    Text(
                        text = "Confirm",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
            }
            if(showDialog){
                AlertDialog(
                    modifier = Modifier.height(150.dp).width(300.dp),
                    backgroundColor = Color(backgroundColor),

                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            text = "Fill the necessary fields",
                            modifier= Modifier.padding(start=40.dp,top=20.dp),
                            fontWeight = FontWeight.Bold
                        )
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
                                    showDialog = false
                                },
                                modifier = Modifier
                                    .padding(start=50.dp,top=60.dp).width(200.dp)


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

        }
    }

}