package com.example.s2m.android.view.merchantPaymentScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.Routes
import com.example.s2m.android.util.backgroundColor
import com.example.s2m.android.util.topBarColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MerchantPaymentScreen(
    navController: NavController
){
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(topBarColor),
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
                            text = "Profile settings", modifier = Modifier
                                .weight(1f),
                            color= Color.White
                        ) }
                })
        },
        backgroundColor = Color(backgroundColor),
    ){
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.qr_code),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                        shape = RoundedCornerShape(50),
                        onClick = { navController.navigate(Routes.MerchantScanner.name) },
                        modifier = Modifier
                            .width(300.dp)
                            .height(45.dp)
                    ) {
                        Text(
                            text = "Scan QR code",
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                    Divider(
                        color = Color.Black, // Change the color of the horizontal line
                        thickness = 1.dp, // Adjust the thickness of the horizontal line
                        modifier = Modifier.fillMaxWidth() // Fill the available width
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                        shape = RoundedCornerShape(50),
                        onClick = { navController.navigate(Routes.Merchant1.name) },
                        modifier = Modifier
                            .width(300.dp)
                            .height(45.dp)
                    ) {
                        Text(
                            text = "Use A Phone Number",
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                }


        }
    }
}}