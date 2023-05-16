package com.example.bankingapp.android.util

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Open(scaffoldState: ScaffoldState) {
    LaunchedEffect(scaffoldState.drawerState) {
        val open=scaffoldState.drawerState.open()
    }
}
