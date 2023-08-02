package com.example.s2m.android.view

import LoadingAnimation
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.LocationPermissionCallback
import com.example.s2m.android.R
import com.example.s2m.android.getHash
import com.example.s2m.android.util.*
import com.example.s2m.util.LoginErrorType
import com.example.s2m.viewmodel.ForexViewModel
import com.example.s2m.viewmodel.LoginViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
 fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController,
    forexViewModel: ForexViewModel= viewModel(),
    callback: LocationPermissionCallback
) {

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val login: String by loginViewModel.login.collectAsState()
    val password: String by loginViewModel.password.collectAsState()
    val activeTextField = remember { mutableStateOf<TextFieldType?>(TextFieldType.PASSWORD) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val maxLoginLength = 9
    val maxPasswordLength = 8
    val context = LocalContext.current
    val loading by loginViewModel.isLoading.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()


    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginViewModel.LoginState.Success -> navController.navigate(Routes.Welcome.name)
            is LoginViewModel.LoginState.Error -> {
                val errorType = (loginState as LoginViewModel.LoginState.Error).errorType
                when (errorType) {
                    LoginErrorType.Api -> Toast.makeText(context, "API error", Toast.LENGTH_SHORT).show()
                    LoginErrorType.Http -> Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                    LoginErrorType.Connection -> Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }
    Box( modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.pexels_pixabay_268533),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    Scaffold(
        backgroundColor = Color(0xffE5FBFE),
        bottomBar = { BottomNavLogin(navController = navController, currentScreen = "welcome", forexViewModel = forexViewModel
        ) { callback.requestLocationPermissions() }
        }
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
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                readOnly = false,
                keyboardActions = KeyboardActions { keyboardController?.hide() },
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(10.dp)
                    )
                    ,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // Customize the focused border color here
                    unfocusedBorderColor = Color.Transparent, // Customize the unfocused border color here
                    disabledBorderColor = Color.Transparent, // Customize the disabled border color here
                    textColor = Color.Black
                ),
                value = login,
                onValueChange = {
                    if (it.length <= 9) loginViewModel.onLoginTextChanged(it)
                },

                label = { Text(text = "Login", color = Color.Black) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                readOnly = true,
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, Color.Black),
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
                    textColor = Color.Black
                ),

                value = password,
                onValueChange = { if (it.length <= 8) loginViewModel.onPasswordTextChanged(it) },
                label = { Text("Password", color = Color.Black) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None)


            )
            Spacer(modifier = Modifier.height(16.dp))

            activeTextField.value?.let { it1 ->
                CustomKeyboard1(
                    activeTextField = it1,
                    onKeyPressed = { key, activeTextField ->
                        if (activeTextField == TextFieldType.USERNAME) {
                            val newLogin = (login + key).take(maxLoginLength)
                            loginViewModel.onLoginTextChanged(newLogin)

                        } else if (activeTextField == TextFieldType.PASSWORD) {
                            val newPass = (password + key).take(maxPasswordLength)
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

            Box {
                Button(
                    shape = RoundedCornerShape(50),
                    onClick = {
                        loginViewModel.login2(login, getHash(password))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff112D4E)),
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 50.dp)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                    )
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff112D4E)),
                shape = RoundedCornerShape(50),
                onClick = {
                    println(loading)
                   loginViewModel.onLoadingChanged(true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        // After the task is completed, set isLoading to false to hide the loading animation.
                        loginViewModel.onLoadingChanged(false)

                    }, 5000)
                },
                modifier = Modifier
                    .width(300.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                )
            }

           /* if (loginState == LoginViewModel.LoginState.Success){
                navController.navigate(Routes.Welcome.name)
            }else if (loginState == LoginViewModel.LoginState.Error(LoginErrorType.Http)){
                navController.navigate(Routes.Contact.name)
            }*/
            /*when (loginState) {
                is LoginViewModel.LoginState.Success -> navController.navigate("welcome")
                is LoginViewModel.LoginState.Error -> {
                    val errorType = (loginState as LoginViewModel.LoginState.Error).errorType
                    when (errorType) {
                        LoginErrorType.Api -> Toast.makeText(context, "API error", Toast.LENGTH_SHORT).show()
                        LoginErrorType.Http -> Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                        LoginErrorType.Connection -> Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> Unit // Handle other states if needed
            }*/
            if (loading) {
                AlertDialog(
                    backgroundColor = Color.Transparent,
                    onDismissRequest = { },
                    properties = DialogProperties(dismissOnClickOutside = false),
                    buttons = {},
                    title = { },
                    text = {
                            LoadingAnimation(isLoading = loading)
                    }
                )
            }
        }
    }
}
 }
enum class TextFieldType {
    USERNAME, PASSWORD
}





