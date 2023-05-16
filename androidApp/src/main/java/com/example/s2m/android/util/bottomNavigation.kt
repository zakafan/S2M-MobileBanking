package com.example.bankingapp.android.util

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNav(navController: NavController,currentScreen:String){

    BottomNavigation {
        BottomNavigationItem(
            selected = currentScreen=="welcome" ,
            onClick = { navController.navigate("welcome") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        BottomNavigationItem(
            selected = currentScreen == "alerts",
            onClick = { /* Handle navigation */ },
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Alerts") },
            label = { Text("Alerts") }
        )
        BottomNavigationItem(
            selected = currentScreen == "beneficiary",
            onClick = { navController.navigate("beneficiary") },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Beneficiary") },
            label = { Text("Beneficiary") }
        )
        BottomNavigationItem(
            selected = currentScreen == "activity",
            onClick = {  },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Activity") },
            label = { Text("Activity") }
        )
    }
}