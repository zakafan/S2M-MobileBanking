package com.example.s2m.android.view.beneficiaryScreen


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.Routes
import com.example.s2m.android.util.backgroundColor
import com.example.s2m.android.util.topBarColor
import com.example.s2m.android.view.AutoSlidingCarousel
import com.example.s2m.android.view.WalletCard
import com.example.s2m.viewmodel.BeneficiaryViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBeneficiaryScreen(
    navController: NavController,
    addBeneficiaryViewModel: BeneficiaryViewModel = viewModel()

){

    val name:String     by addBeneficiaryViewModel.beneficiaryName.collectAsState()
    val phone:String    by addBeneficiaryViewModel.phone.collectAsState()
    var showDialog      by remember { mutableStateOf(false) }


    Scaffold (
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
                                contentDescription = "Bck",
                                tint = Color.White,
                            ) }

                        Text(
                            text = "Add beneficiary", modifier = Modifier
                                .weight(1f),
                            color= Color.White
                        ) }
                })
        },
        backgroundColor = Color(backgroundColor),

        ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),

            ) {
            Spacer(modifier = Modifier.height(100.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Beneficiary Wallet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color=Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(backgroundColor),
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = name,
                onValueChange = {addBeneficiaryViewModel.onBeneficiaryNameChanged(it) },
                label = { Text("Beneficiary Name*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),

            )
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(backgroundColor),
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = phone,
                onValueChange = { if (it.length <= 9) addBeneficiaryViewModel.onPhoneChanged(it) },
                label = { Text("Phone number*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                shape = RoundedCornerShape(50),
                onClick = {
                          if(name == "" || phone == ""){
                              showDialog = true
                          }else{
                              navController.navigate(Routes.ValidBenef.name)
                          }

                },
                modifier = Modifier
                    .width(500.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
            if (showDialog) {
                AlertDialog(
                    modifier = Modifier
                        .height(150.dp)
                        .width(300.dp),

                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(text = "Fill the necessary fields",modifier=Modifier.padding(start=40.dp,top=20.dp))
                    },
                    buttons = {
                        Column {

                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                                shape = RoundedCornerShape(50),
                                onClick = {
                                    showDialog = false

                                },
                                modifier = Modifier
                                    .padding(start = 50.dp, top = 60.dp)
                                    .width(200.dp)


                            ) {

                                Text(
                                    text = "OK",
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                )
            }

        }



    }
}