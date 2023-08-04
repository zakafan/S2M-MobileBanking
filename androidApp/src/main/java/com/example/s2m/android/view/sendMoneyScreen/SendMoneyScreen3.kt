package com.example.s2m.android.view.sendMoneyScreen

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
import com.example.s2m.android.getHash
import com.example.s2m.android.util.*
import com.example.s2m.model.User
import com.example.s2m.util.SendMoneyErrorType
import com.example.s2m.util.TransferErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.SendMoneyViewModel
import com.example.s2m.viewmodel.TransferViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SendMoneyScreen3(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    sendMoneyViewModel: SendMoneyViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel()
){

    val user: User by loginViewModel.user.collectAsState()
    val sendMoneyState by sendMoneyViewModel.sendMoneyState.collectAsState( )
    var showDialog1 by remember { mutableStateOf(false) }
    var otpValue by remember{ mutableStateOf("") }
    val loading by sendMoneyViewModel.isLoading.collectAsState()

    LaunchedEffect(sendMoneyState){
        when(sendMoneyState){
            is SendMoneyViewModel.SendMoneyState.Success -> navController.navigate(Routes.Send4.name)
            is SendMoneyViewModel.SendMoneyState.Error -> {
                val errorType = (sendMoneyState as SendMoneyViewModel.SendMoneyState.Error).errorType
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
                                contentDescription = "Back",
                                tint = Color.White,
                            ) }

                        Text(
                            text = "Money Send", modifier = Modifier
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

            Spacer(modifier = Modifier.height(25.dp))



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
                        sendMoneyViewModel.onPinChanged(otpValue)
                        sendMoneyViewModel.sendMoney(sendMoneyViewModel.amount.value,sendMoneyViewModel.memo.value,sendMoneyViewModel.toPhone.value,
                            getHash(sendMoneyViewModel.pin.value),sendMoneyViewModel.beneficiaryName.value,sendMoneyViewModel.identityNumber.value,sendMoneyViewModel.notify.value,
                            getHash(sendMoneyViewModel.secretCode.value ))
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
                    Text(text = "Invalid PIN!!",modifier=Modifier.padding(start=80.dp,top=20.dp),
                        fontWeight = FontWeight.Bold)
                },
                buttons = {
                    Column {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                                topBarColor)),
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
        if (loading){
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