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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.model.Alert
import com.example.s2m.model.User
import com.example.s2m.viewmodel.AlertsViewModel
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlertsScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    alertsViewModel: AlertsViewModel= viewModel(),
    logoutViewModel: LogoutViewModel = viewModel()
){

    val alertResponse by alertsViewModel.alertResponse.collectAsState()
    val alertsList by alertsViewModel.alertResponse.collectAsState()
    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current



    DisposableEffect(Unit) {
        onDispose {
            if (!alertsViewModel.isDetailScreenVisible.value) {
                alertsViewModel.clearState()
                alertsViewModel.getUnreadedAlert()
            }
        }
    }

    Scaffold(

        backgroundColor = Color.LightGray,
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {
                TopAppBar(
                    backgroundColor = Color.Black,
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
                                                tint = Color(0xff00E0F7),
                                                modifier = Modifier.padding(end = 25.dp)
                                            )
                                        }

                                        Text(
                                            text = "Notifications", modifier = Modifier
                                                .weight(1f)
                                                .padding(bottom = 50.dp),
                                            color= Color.White
                                        )

                                        IconButton(
                                            onClick = { /*TODO*/ },
                                            modifier = Modifier.padding(bottom = 50.dp)
                                        ) {
                                            Icon(
                                                Icons.Filled.Notifications,
                                                contentDescription = "notification",
                                                tint = Color(0xff00E0F7)
                                            )
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Balance: ${it.equipmentList[0].balance} MAD", color = Color.White,modifier= Modifier.padding(top=80.dp,end=70.dp), fontSize = 25.sp, fontStyle = FontStyle.Italic)
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
            BottomNav(navController = navController,"beneficiary")
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (alertsList?.alertList != null) {
                if (alertsList!!.alertList?.isNotEmpty() == true) {
                    val readStateMap = remember { mutableStateMapOf<Int, Boolean>() }
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .background(color = Color.Transparent)
                    ) {
                        itemsIndexed(alertResponse?.alertList ?: emptyList()) { _, alert ->
                            val readState = readStateMap.getOrPut(alert.iden) { alert.readed }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        ,
                                    elevation = 4.dp,
                                    backgroundColor =if (!readState) Color.DarkGray else Color.White,
                                    shape = RoundedCornerShape(15.dp),
                                    onClick = {
                                        alertsViewModel.onAlertSelected(alert)
                                        alertsViewModel.onChangeVisibility(true)
                                        navController.navigate("alert")
                                        alertsViewModel.readAlert(alert.iden)
                                        readStateMap[alert.iden] = true}
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
                                                    color = Color.Gray,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
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
                } else {
                    Text(
                        text = "No Beneficiaries added yet!",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                // Show the "Load more" button at the bottom
                Box(
                    modifier=Modifier.height(120.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                        shape = RoundedCornerShape(50),
                        onClick = {
                             // Increment the current page number
                            alertsViewModel.loadNextPage()
                            println(alertsViewModel.currentPage)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .height(45.dp)
                    ) {
                        Text(
                            text = "Load more",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }


    }

}