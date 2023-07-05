package com.example.s2m.android.view.settingsScreens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.getHash
import com.example.s2m.android.util.*
import com.example.s2m.util.SendMoneyErrorType
import com.example.s2m.viewmodel.ProfileViewModel
import com.example.s2m.viewmodel.SendMoneyViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChangePasswordPin(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()

){
    var otpValue by remember{ mutableStateOf("") }
    val pin : String by profileViewModel.pin.collectAsState()
    var showDialog1 by remember { mutableStateOf(false) }

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
                            text = "Change Password", modifier = Modifier
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
                        profileViewModel.onPinChanged(otpValue)
                        if(
                                profileViewModel.changePassword(
                                    getHash(profileViewModel.oldPassword.value) ,
                                    getHash(profileViewModel.newPassword.value) ,
                                    getHash(profileViewModel.confirmNewPassword.value) ,
                                    getHash( profileViewModel.pin.value))
                                ==  ProfileViewModel.ChangePasswordState.Success){
                            profileViewModel.clearState()
                            navController.navigate(Routes.Welcome.name)
                        }
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
                modifier = Modifier.height(150.dp).width(300.dp),
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