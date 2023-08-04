package com.example.s2m.android.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.model.User
import com.example.s2m.util.SendMoneyErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.MerchantPaymentViewModel

@Composable
fun DrawerContent(user: User, loginViewModel: LoginViewModel, navController: NavController, logoutViewModel: LogoutViewModel){

    val loading by logoutViewModel.isLoading.collectAsState()
    val loggedOut by logoutViewModel.loggedOut.collectAsState()

    LaunchedEffect(loggedOut){
        if(loggedOut){
            loginViewModel.clearState()
            navController.navigate("login"){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
            logoutViewModel.logOut()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // User Info Section
        Box(
            modifier = Modifier
                .height(160.dp)
                .background(color = Color(topBarColor)),
            contentAlignment = Alignment.Center
        ) {
            // User Info Placeholder Image and Text
            // Here, you can add a circular image, login and phone number of the user
            UserInfoDrawer(user = user)
        }

        // Navigation Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(Color(backgroundColor))

        ) {
            // Home Icon and Text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
                Text(
                    text = "Home",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp
                )
            }

            // Wallet Icon and Text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Notifications, contentDescription = "Alerts")
                Text(
                    text = "Alerts",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp
                )
            }

            // Profile Icon and Text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
                Text(
                    text = "Profile",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp
                )
            }
        }

        // Logout Button
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
            onClick = {
                      logoutViewModel.logout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(top = 440.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "Logout",tint = Color.White)
                Text(
                    text = "Logout",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }


}

@Composable
fun UserInfoDrawer(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User Info Placeholder Image

        Image(
            painterResource(id = R.drawable.s2m_logo),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),

            )

        Spacer(modifier = Modifier.height(14.dp))

        // Login Text
        Text(
            text = user.responseLogin?.lastName ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Text
        Text(
            text = user.responseLogin?.phone ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}