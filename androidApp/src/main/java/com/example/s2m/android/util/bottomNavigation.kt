package com.example.s2m.android.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.s2m.android.R

@Composable
fun BottomNav(navController: NavController,currentScreen:String){
    val bottomBarShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Surface(shape = bottomBarShape) {
        BottomNavigation (
            backgroundColor = Color.White,
            modifier = Modifier.height(60.dp)

            ){
            BottomNavigationItem(
                selected = currentScreen=="welcome" ,
                onClick = { navController.navigate("welcome") },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color(0xFF37C8C3)) },
                label = { Text("Home", color =Color.Black ) }
            )
            BottomNavigationItem(
                selected = currentScreen == "alerts",
                onClick = { /* Handle navigation */ },
                icon = { Icon(Icons.Filled.Notifications, contentDescription = "Alerts", tint = Color(0xFF37C8C3)) },
                label = { Text("Alerts", color =Color.Black) }
            )
            BottomNavigationItem(
                selected = currentScreen == "beneficiary",
                onClick = { navController.navigate("beneficiary") },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Beneficiary", tint = Color(0xFF37C8C3)) },
                label = { Text("Beneficiary", color =Color.Black) }
            )
            BottomNavigationItem(
                selected = currentScreen == "activity",
                onClick = {  },
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Activity", tint = Color(0xFF37C8C3)) },
                label = { Text("Activity", color =Color.Black) }
            )
        }
    }
    }

@Composable
fun BottomNavLogin(navController: NavController,currentScreen:String){
    val bottomBarShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Surface(shape = bottomBarShape) {
        BottomNavigation (
            backgroundColor = Color.White,
            modifier = Modifier.height(60.dp)

        ){
            BottomNavigationItem(
                selected = currentScreen=="" ,
                onClick = {  },
                icon = { Icon(Icons.Filled.Email, contentDescription = "Contact", tint = Color(0xFF37C8C3)) },
                label = { Text("Contact", color =Color.Black ) }
            )
            BottomNavigationItem(
                selected = currentScreen == "",
                onClick = { /* Handle navigation */ },
                icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Locations", tint = Color(0xFF37C8C3)) },
                label = { Text("Location", color =Color.Black) }
            )
            BottomNavigationItem(
                selected = currentScreen == "",
                onClick = {  },
                icon = { Icon(painter = painterResource(R.drawable.forex), contentDescription = "Forex", tint = Color(0xFF37C8C3), modifier = Modifier.size(30.dp)) },
                label = { Text("Forex", color =Color.Black) }
            )

        }
    }
}


