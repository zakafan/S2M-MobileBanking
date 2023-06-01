package com.example.s2m.android.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.relay.compose.BoxScopeInstanceImpl.align

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LocationsScreen(
    navController: NavController
){


    Scaffold (
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                modifier = Modifier.height(80.dp),
                title = {

                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Row(
                            modifier = Modifier.padding(bottom = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {navController.popBackStack()

                                },
                                modifier = Modifier.padding(bottom = 50.dp)
                            ) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "Back Arrow",
                                    tint = Color(0xff00E0F7),
                                    //  modifier = Modifier.padding(end = 1.dp)
                                )
                            }

                            Text(
                                text = "Locations", modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 50.dp),
                                color= Color.White
                            )

                        }
                    }

                })
        },
        backgroundColor = Color.LightGray,

        ){

    }
}