package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.getHash
import com.example.s2m.android.util.Routes
import com.example.s2m.android.util.backgroundColor
import com.example.s2m.android.util.topBarColor
import com.example.s2m.util.LoginErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.ProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel(),
    loginViewModel: LoginViewModel     = viewModel()
){

    val oldPassword        :String  by profileViewModel.oldPassword.collectAsState()
    val newPassword        :String  by profileViewModel.newPassword.collectAsState()
    val confirmNewPassword :String  by profileViewModel.confirmNewPassword.collectAsState()
    val isButtonClicked = remember { mutableStateOf(false) }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),

            ) {

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(150.dp)
                    .background(color = Color(0xffAFD3E2), shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "Person Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(Color(0xff1D267D))
                    )
                    loginViewModel.user.value.responseLogin?.let { it1 ->
                        Text(
                            text = it1.firstName ,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                shape = RoundedCornerShape(50),
                onClick = {isButtonClicked.value = !isButtonClicked.value} ,
                modifier = Modifier
                    .width(500.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Change Password",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
            if (isButtonClicked.value) {
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black, // Change the text color here
                        cursorColor = Color.Blue, // Change the cursor color here
                        focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color(backgroundColor)

                    ),
                    value = oldPassword,
                    onValueChange = { if (it.length <= 8) profileViewModel.onOldPasswordChanged(it)},
                    label = {
                        Text(
                            "Old password",
                            color = Color.Black,
                            textAlign = TextAlign.Center, // Center align the label text
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(70.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            border = BorderStroke(2.dp, color = Color.Black),
                            shape = RoundedCornerShape(20)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.NumberPassword,

                    )
                )
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black, // Change the text color here
                        cursorColor = Color.Blue, // Change the cursor color here
                        focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color(backgroundColor)

                    ),
                    value = newPassword,
                    onValueChange = { if (it.length <= 8) profileViewModel.onNewPasswordChanged(it)},
                    label = {
                        Text(
                            "New password",
                            color = Color.Black,
                            textAlign = TextAlign.Center, // Center align the label text
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(70.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            border = BorderStroke(2.dp, color = Color.Black),
                            shape = RoundedCornerShape(20)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.NumberPassword,

                        )
                )
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black, // Change the text color here
                        cursorColor = Color.Blue, // Change the cursor color here
                        focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color(backgroundColor)

                    ),
                    value = confirmNewPassword,
                    onValueChange = { if (it.length <= 8) profileViewModel.onConfirmNewPasswordChanged(it)},
                    label = {
                        Text(
                            "Confirm password",
                            color = Color.Black,
                            textAlign = TextAlign.Center, // Center align the label text
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(70.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            border = BorderStroke(2.dp, color = Color.Black),
                            shape = RoundedCornerShape(20)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.NumberPassword,
                        )
                )
                Button(
                    shape = RoundedCornerShape(50),
                    onClick = {navController.navigate(Routes.ChangePassword.name)},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff112D4E)),
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                shape = RoundedCornerShape(50),
                onClick = {isButtonClicked.value = !isButtonClicked.value} ,
                modifier = Modifier
                    .width(500.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Change Pin",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }
    }
}