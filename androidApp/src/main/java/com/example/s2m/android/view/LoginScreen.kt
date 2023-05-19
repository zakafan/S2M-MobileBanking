package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.getHash
import com.example.s2m.android.util.BottomNavLogin
import com.example.s2m.util.LoginErrorType
import com.example.s2m.viewmodel.LoginViewModel



@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
 fun LoginScreen(loginViewModel: LoginViewModel = viewModel(), navController: NavController) {
    val login: String by loginViewModel.login.collectAsState()
    val password: String by loginViewModel.password.collectAsState()
    val activeTextField = remember { mutableStateOf<TextFieldType?>(TextFieldType.USERNAME) }
    //val transferViewModel = TransferViewModel(repository = TransferRepository(loginViewModel=loginViewModel))
    val keyboardController = LocalSoftwareKeyboardController.current
    val MAX_USERNAME_LENGTH = 9
    val MAX_PASSWORD_LENGTH = 8

    Box( modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.pexels_pixabay_268533),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    Scaffold(
        backgroundColor = Color.Black,
        bottomBar = { BottomNavLogin(navController = navController, currentScreen = "welcome") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Rectangle()
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                readOnly = true,
                keyboardActions = KeyboardActions { keyboardController?.hide() },
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, Color(0xff00E0F7)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onFocusChanged {
                        if (it.isFocused) {
                            activeTextField.value = TextFieldType.USERNAME
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // Customize the focused border color here
                    unfocusedBorderColor = Color.Transparent, // Customize the unfocused border color here
                    disabledBorderColor = Color.Transparent, // Customize the disabled border color here
                    textColor = Color.White
                ),
                value = login,
                onValueChange = {
                    if (it.length <= 9) loginViewModel.onLoginTextChanged(it)
                },

                label = { Text(text = "Login", color = Color.White) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                readOnly = true,
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, Color(0xff00E0F7)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onFocusChanged {
                        if (it.isFocused) {
                            activeTextField.value = TextFieldType.PASSWORD
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // Customize the focused border color here
                    unfocusedBorderColor = Color.Transparent, // Customize the unfocused border color here
                    disabledBorderColor = Color.Transparent, // Customize the disabled border color here
                    textColor = Color.White
                ),

                value = password,
                onValueChange = { if (it.length <= 8) loginViewModel.onPasswordTextChanged(it) },
                label = { Text("Password", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None)


            )
            Spacer(modifier = Modifier.height(16.dp))

            activeTextField.value?.let { it1 ->
                CustomKeyboard1(
                    activeTextField = it1,
                    onKeyPressed = { key, activeTextField ->
                        if (activeTextField == TextFieldType.USERNAME) {
                            val newLogin = (login + key).take(MAX_USERNAME_LENGTH)
                            loginViewModel.onLoginTextChanged(newLogin)

                        } else if (activeTextField == TextFieldType.PASSWORD) {
                            val newPass = (password + key).take(MAX_PASSWORD_LENGTH)
                            loginViewModel.onPasswordTextChanged(newPass)

                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onBackspacePressed = {
                        activeTextField.value?.let { textFieldType ->
                            when (textFieldType) {
                                TextFieldType.USERNAME -> {
                                    if (login.isNotEmpty()) {
                                        loginViewModel.onLoginTextChanged(login.dropLast(1))
                                    }
                                }
                                TextFieldType.PASSWORD -> {
                                    if (password.isNotEmpty()) {
                                        loginViewModel.onPasswordTextChanged(password.dropLast(1))
                                    }
                                }
                            }
                        }
                    },
                    onDeletePressed = {
                        activeTextField.value?.let { textFieldType ->
                            when (textFieldType) {
                                TextFieldType.USERNAME -> {
                                    if (login.isNotEmpty()) {
                                        loginViewModel.onLoginTextChanged("")
                                    }
                                }
                                TextFieldType.PASSWORD -> {
                                    if (password.isNotEmpty()) {
                                        loginViewModel.onPasswordTextChanged("")
                                    }
                                }
                            }
                        }

                    }
                )

            }


            val context = LocalContext.current
            Button(
                shape = RoundedCornerShape(50),
                onClick = {
                    if (loginViewModel.login2(
                            login, getHash(password)
                        ) == LoginViewModel.LoginState.Success
                    ) {
                        navController.navigate("welcome")
                    } else if (loginViewModel.login2(
                            login,
                            getHash(password)
                        ) == LoginViewModel.LoginState.Error(errorType = LoginErrorType.Api)
                    ) {
                        Toast.makeText(context, "API error", Toast.LENGTH_SHORT).show()
                    } else if (loginViewModel.login2(
                            login,
                            getHash(password)
                        ) == LoginViewModel.LoginState.Error(errorType = LoginErrorType.Http)
                    ) {
                        Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT)
                            .show()
                    } else if (loginViewModel.login2(
                            login,
                            getHash(password)
                        ) == LoginViewModel.LoginState.Error(errorType = LoginErrorType.Connection)
                    ) {
                        Toast.makeText(
                            context,
                            "Check your internet connection",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show()
                    }

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),

                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 50.dp)

            )
            {
                Text(
                    text = "Login",
                    color = Color.Black,
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                shape = RoundedCornerShape(50),
                onClick = {
                    Toast.makeText(context, "Still in development", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .width(300.dp)


            ) {

                Text(
                    text = "Sign Up",
                    color = Color.Black,
                )
            }
        }
    }
}


    }

@Composable
fun Rectangle() {
    Box(
        modifier = Modifier
            .width(width = 213.dp)
            .height(height = 54.dp)
            .background(color = Color.White.copy(alpha = 0.78f), RoundedCornerShape(8.dp))
    ){
        Image(
            painter = painterResource(id = R.drawable.s2m_logo), // Replace with your logo resource
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun KeyboardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        border= BorderStroke(1.dp,Color.White),
        colors=ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick,
        shape = CircleShape, // Use CircleShape for circular buttons
        modifier = modifier
            .size(50.dp)
            .padding(4.dp)
    ) {
        Text(text,color=Color.White)
    }
}

@Composable
fun CustomKeyboard1(
    activeTextField:TextFieldType,
    onKeyPressed: (String,TextFieldType) -> Unit,
    modifier: Modifier = Modifier,
    onBackspacePressed: () -> Unit,
    onDeletePressed: () -> Unit
) {
     val shuffledNumbers = remember { (0..9).shuffled() }

    val keyboardKeys = listOf(
        shuffledNumbers.subList(0, 4),
        shuffledNumbers.subList(4, 8),
        shuffledNumbers.subList(8, 10)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 60.dp, vertical = 16.dp) // Adjust the horizontal and vertical padding
    ) {
        keyboardKeys.forEachIndexed { rowIndex, row ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {if (rowIndex == keyboardKeys.size - 1) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.width(200.dp)
                ) {
                    IconButton(onClick = {
                        onDeletePressed()
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)

                    }
                    row.forEach { key ->
                        KeyboardButton(
                            text = key.toString(),
                            onClick = { onKeyPressed(key.toString(),activeTextField) },
                           modifier = Modifier.weight(1f) // Adjust the weight for closer buttons
                        )

                    }

                    IconButton(onClick = {
                        onBackspacePressed()
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear", tint = Color.White)

                    }
                    /*KeyboardButton(
                        text = "X",
                        onClick = {
                            onBackspacePressed()
                        })*/
                }
            } else {
                row.forEach { key ->
                    KeyboardButton(
                        text = key.toString(),
                        onClick = { onKeyPressed(key.toString(),activeTextField) }
                    )
                }
            }
            }
        }
    }

}
enum class TextFieldType {
    USERNAME, PASSWORD
}


