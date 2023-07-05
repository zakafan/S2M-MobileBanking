package com.example.s2m.android.view.sendMoneyScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.android.util.Routes
import com.example.s2m.android.util.topBarColor
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.SendMoneyViewModel
import com.example.s2m.viewmodel.TransferViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SendMoneyScreen4(
    sendMoneyViewModel: SendMoneyViewModel= viewModel(),
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel()
){

    val user: User by loginViewModel.user.collectAsState()
    val currentDateTime = LocalDateTime.now()

// Define the desired date and time format
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

// Format the current date and time
    val formattedDateTime = currentDateTime.format(formatter)

    Scaffold(

        backgroundColor = Color(0xffE5FBFE),
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),

                ) {
                TopAppBar(
                    backgroundColor = Color(topBarColor),
                    title = {
                        Text(text = "Money Send", color = Color.White)
                    }
                )
            }
        },

        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController, logoutViewModel =logoutViewModel )
        },
        bottomBar = {
            BottomNav(navController = navController,"beneficiary")
        }
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(80.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(id = R.drawable.checked),
                    contentDescription = "Success",
                    tint = Color(topBarColor),

                    )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Successful operation",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier
                    .height(2.dp)
                    .width(320.dp)
                    .padding(start = 40.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xffAFD3E2),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("From           : ${user.responseLogin?.phone} ", color = Color.Black)
                    Text("To                : ${sendMoneyViewModel.toPhone.value} ", color = Color.Black)
                    Text("Amount       : ${sendMoneyViewModel.amount.value} MAD", color = Color.Black)
                    Text("Memo          : ${sendMoneyViewModel.memo.value}", color = Color.Black)
                    Text("Fee               : 12.00 MAD", color = Color.Black)
                    Text("Date             : $formattedDateTime ", color = Color.Black)
                    Text("Expiry Date : ${sendMoneyViewModel.expiryDate.value} ", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(50.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        sendMoneyViewModel.clearState()
                        navController.navigate(Routes.Welcome.name)
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
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(120.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        navController.navigate(Routes.Welcome.name)
                    },
                    modifier = Modifier
                        .width(500.dp)
                        .height(45.dp)
                ) {
                    Text(
                        text = "Share",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
            }
        }



    }

}