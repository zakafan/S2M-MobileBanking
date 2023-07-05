package com.example.s2m.android.view.alertScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.android.util.backgroundColor
import com.example.s2m.model.Alert
import com.example.s2m.model.User
import com.example.s2m.viewmodel.AlertsViewModel
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlertsScreen2(
    alert:Alert,
    alertsViewModel: AlertsViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController,
    logoutViewModel: LogoutViewModel = viewModel()
){

    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            alertsViewModel.onChangeVisibility(false)
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
                            text = "Notifications", modifier = Modifier
                                .weight(1f),
                            color=Color.White
                        )

                    }
                })
        },
        bottomBar = {
            BottomNav(navController = navController,"beneficiary")
        }
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            elevation = 4.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(15.dp),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Beneficiary Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = alert.messageType,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    alert.messageTxt?.let { it1 ->
                        Text(
                            text = it1,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = alert.messageTypeIden,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }



    }




}