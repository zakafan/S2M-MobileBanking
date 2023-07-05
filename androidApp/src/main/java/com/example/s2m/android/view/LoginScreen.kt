package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.LocationPermissionCallback
import com.example.s2m.android.R
import com.example.s2m.android.getHash
import com.example.s2m.android.util.BottomNavLogin
import com.example.s2m.android.util.Routes
import com.example.s2m.util.LoginErrorType
import com.example.s2m.viewmodel.ForexViewModel
import com.example.s2m.viewmodel.LoginViewModel
import kotlinx.coroutines.coroutineScope


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
    val isLoading1 by loginViewModel.isLoading.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()
    var loginInProgress by remember { mutableStateOf(false) }





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
                if (loginInProgress) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .then(Modifier.blur(4.dp))
                    )
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }

                Button(
                    shape = RoundedCornerShape(50),
                    onClick = {
                        loginInProgress = true

                        // Perform login asynchronously here
                        // Once the login process is complete, set loginInProgress to false

                        loginViewModel.login2(login, getHash(password)).let { loginState ->
                            when (loginState) {
                                is LoginViewModel.LoginState.Success -> navController.navigate("welcome")
                                is LoginViewModel.LoginState.Error -> {
                                    when (loginState.errorType) {
                                        LoginErrorType.Api -> Toast.makeText(context, "API error", Toast.LENGTH_SHORT).show()
                                        LoginErrorType.Http -> Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                                        LoginErrorType.Connection -> Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else -> {}
                            }
                        }

                        // Once the login process is complete, set loginInProgress to false
                        loginInProgress = false
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
                    navController.navigate(Routes.Withdrawal3.name
                    )
                    Toast.makeText(context, "Still in development", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .width(300.dp)


            ) {

                Text(
                    text = "Sign Up",
                    color = Color.White,
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
            .background(color = Color(0xffE5FBFE), RoundedCornerShape(8.dp))
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
        border= BorderStroke(1.dp,Color.Black),
        colors=ButtonDefaults.buttonColors(backgroundColor = Color(0xffE5FBFE)),
        onClick = onClick,
        shape = CircleShape, // Use CircleShape for circular buttons
        modifier = modifier
            .size(50.dp)
            .padding(4.dp)
    ) {
        Text(text,color=Color.Black)
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
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color(0xff112D4E))

                    }
                    row.forEach { key ->
                        KeyboardButton(
                            text = key.toString(),
                            onClick = { onKeyPressed(key.toString(),activeTextField) },
                           modifier = Modifier.weight(1f),
                            // Adjust the weight for closer buttons
                        )

                    }

                    IconButton(onClick = {
                        onBackspacePressed()
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear", tint = Color(0xff112D4E))

                    }
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

@Composable
fun LoadingAnimation1(
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1000
) {

    // circle's scale state
    var circleScale by remember {
        mutableStateOf(0f)
    }

    // animation
    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )

    // This is called when the app is launched
    LaunchedEffect(Unit) {
        circleScale = 1f
    }

    // animating circle
    Box(
        modifier = Modifier
            .size(size = 64.dp)
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = 4.dp,
                color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            )
    ) {

    }
}

@Composable
fun LoadingButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isLoading,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp),
                color = MaterialTheme.colors.primary,
            )
        } else {
            Text(text)
        }
    }
}

