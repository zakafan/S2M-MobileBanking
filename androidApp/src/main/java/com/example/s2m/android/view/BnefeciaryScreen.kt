package com.example.s2m.android.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BeneficiaryScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController
) {

    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()


    val beneficiaryList = user.responseLogin?.beneficiaryList

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
                                            text = "Beneficiary List", modifier = Modifier
                                                .weight(1f)
                                                .padding(bottom = 50.dp),
                                            color=Color.White
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
                                    Text(text = "Balance: ${it.equipmentList[0].balance} MAD", color = Color.White,modifier=Modifier.padding(top=80.dp,end=70.dp), fontSize = 25.sp, fontStyle = FontStyle.Italic)
                                }
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = Color.Black,
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Add Beneficiary", tint = Color(0xff00E0F7))
                }
            )
        },
        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController)
        },
        bottomBar = {
            BottomNav(navController = navController,"beneficiary")
        }
    ) {


        if (beneficiaryList != null) {
            if (beneficiaryList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .background(color = Color.Transparent)
                ) {
                    itemsIndexed(beneficiaryList) { _, beneficiary ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            elevation = 4.dp,
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(15.dp)
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
                                        text = beneficiary.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color=Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    beneficiary.accountNumber?.let { it1 ->
                                        Text(
                                            text = it1,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = beneficiary.mobilePhone,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }

                                // Edit Button
                                IconButton(
                                    onClick = {
                                        /* navController.navigate(
                                            Screens.AddBeneficiaryScreen.route +
                                                    "/${beneficiary.id}"
                                        )*/
                                    }
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit Beneficiary",)
                                }

                                // Delete Button
                                IconButton(
                                    onClick = {
                                        // loginViewModel.deleteBeneficiary(beneficiary)
                                    }
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete Beneficiary",
                                        tint = Color.Red
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
        }




    }

}

