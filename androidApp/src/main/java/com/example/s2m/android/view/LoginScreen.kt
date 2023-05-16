package com.example.s2m.android.view

import android.graphics.Canvas
import android.graphics.RectF
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.getHash
import com.example.s2m.repository.TransferRepository
import com.example.s2m.util.LoginErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.TransferViewModel


@Composable
 fun LoginScreen(loginViewModel: LoginViewModel = viewModel(), navController: NavController) {
    val login: String by loginViewModel.login.collectAsState()
    val password: String by loginViewModel.password.collectAsState()
    //val loginState = loginViewModel.loginState.collectAsState()
    val transferViewModel = TransferViewModel(repository = TransferRepository(loginViewModel=loginViewModel))


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = login,
            onValueChange = {
                if (it.length <= 9) loginViewModel.onLoginTextChanged(it)
            },

            label = { Text(text = "Login") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { if (it.length <= 8) loginViewModel.onPasswordTextChanged(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(16.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                if (loginViewModel.login2(login, getHash(password)
                    ) == LoginViewModel.LoginState.Success
                ) {
                    navController.navigate("welcome")
                    transferViewModel.transfer("100","de","+212775252046","ao2B8O9B1en1gAmTSgKgRC5zEs3ayF6x6mInt+ayW/Y+gtKHtdWizV7QMspJPsqMALhai0/7VrfjtmqXyD+5OA==")
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
                    Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        )
        {
            Text("Login")
        }


    }
}

