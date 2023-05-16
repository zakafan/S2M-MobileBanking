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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bankingapp.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BeneficiaryScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController
) {

    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
   // val open = Open(scaffoldState = scaffoldState)

    val beneficiaryList = user.responseLogin?.beneficiaryList

    Scaffold(

        backgroundColor = Color.LightGray,
        topBar = {
            TopAppBar(
                title = { Text(text = "Beneficiary list") },
                navigationIcon = {
                    IconButton(onClick = { } ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = Color.Gray,
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Add Beneficiary")
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
                                        fontWeight = FontWeight.Bold
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

