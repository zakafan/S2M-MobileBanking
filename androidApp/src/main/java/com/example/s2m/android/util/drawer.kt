package com.example.s2m.android.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.s2m.android.view.UserInfoDrawer
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel

@Composable
fun DrawerContent(user: User, loginViewModel: LoginViewModel, navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // User Info Section
        Box(
            modifier = Modifier
                .height(160.dp)
                .background(color = Color.LightGray),
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
            onClick = {
                loginViewModel.clearState()
                navController.navigate("login"){
                    popUpTo("login"){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(top = 440.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
                Text(
                    text = "Logout",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }


}