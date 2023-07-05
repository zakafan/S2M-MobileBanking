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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.viewmodel.ForexViewModel
import kotlinx.coroutines.delay



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
                onClick = { navController.navigate(Routes.Welcome.name) },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color(0xff112D4E)) },
                label = { Text("Home", color =Color.Black ) }
            )
            BottomNavigationItem(
                selected = currentScreen == "alerts",
                onClick = {  },
                icon = { Icon(Icons.Filled.Notifications, contentDescription = "Alerts", tint = Color(0xff112D4E)) },
                label = { Text("Alerts", color =Color.Black) }
            )
            BottomNavigationItem(
                selected = currentScreen == "beneficiary",
                onClick = { navController.navigate(Routes.Beneficiary.name) },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Beneficiary", tint = Color(0xff112D4E)) },
                label = { Text("Beneficiary", color =Color.Black) }
            )
            BottomNavigationItem(
                selected = currentScreen == "activity",
                onClick = { navController.navigate(Routes.Profile.name) },
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Profile", tint = Color(0xff112D4E)) },
                label = { Text("Profile", color =Color.Black) }
            )
        }
    }
    }

@Composable
fun BottomNavLogin(navController: NavController,currentScreen:String,forexViewModel: ForexViewModel = viewModel(),perm:()->Unit){
    val bottomBarShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    Surface(shape = bottomBarShape) {
        BottomNavigation (
            backgroundColor = Color.White,
            modifier = Modifier.height(60.dp)

        ){
            BottomNavigationItem(
                selected = currentScreen=="" ,
                onClick = { navController.navigate(Routes.Contact.name) },
                icon = { Icon(Icons.Filled.Email, contentDescription = "Contact", tint = Color(0xff112D4E)) },
                label = { Text("Contact", color =Color.Black ) }
            )
            BottomNavigationItem(
                selected = currentScreen == "",
                onClick = {
                  //  perm
                   // navController.navigate(Routes.Locations.name)
                          },
                icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Locations", tint = Color(0xff112D4E)) },
                label = { Text("Location", color =Color.Black) }
            )
            BottomNavigationItem(
                selected = currentScreen == "",
                onClick = { forexViewModel.getForexRate()
                            navController.navigate(Routes.Forex.name)
                          },
                icon = { Icon(painter = painterResource(R.drawable.forex), contentDescription = "Forex", tint = Color(0xff112D4E), modifier = Modifier.size(30.dp)) },
                label = { Text("Forex", color =Color.Black) }
            )

        }
    }
}

