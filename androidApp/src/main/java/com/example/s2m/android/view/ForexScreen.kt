package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.topBarColor
import com.example.s2m.model.Forex
import com.example.s2m.viewmodel.ForexViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun ForexScreen(
    forexViewModel: ForexViewModel= viewModel(),
    navController: NavController
    ){
    val forexList= forexViewModel.forexList.value.forexList
    val currentDate = remember { LocalDateTime.now() }
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy")

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
                                        contentDescription = "Menu",
                                        tint = Color.White,
                                    ) }

                                Text(
                                    text = "Forex Table", modifier = Modifier
                                        .weight(1f),
                                    color=Color.White
                                ) }
                 })
                 },
        backgroundColor = Color(com.example.s2m.android.util.backgroundColor)

            ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = dateFormatter.format(currentDate),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp,start=70.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            if (forexList != null) {
                ForexTable(forexList = forexList)
            }
        }

    }

}

@Composable
fun ForexTable(forexList: List<Forex>) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = " Buy",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Green)
                )
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .background(color = Color.Black)
                )
                Text(
                    text = "Sell ",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Red)
                )
            }
        }

        items(forexList) { forex ->
            ForexRow(forex = forex)
        }
    }
}


@Composable
fun ForexRow(forex: Forex) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = Color(0xffAFD3E2), shape = CircleShape)
        ) {
            Text(
                text = forex.sourceCurrencyAlpha3Code ?: "",
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White
                )
            )
        }
        Text(
            text = forex.sourceCurrencyLabel,
            modifier = Modifier
                .weight(1f)
                .padding(start = 40.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
        )

        Text(
            text = forex.purchaseRate,
            modifier = Modifier.weight(1f).padding(start = 70.dp), color = Color.Black
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 8.dp)
                .background(color = Color.Black), color = Color.Black
        )
        Text(
            text = forex.saleRate,
            modifier = Modifier.weight(1f).padding(start = 50.dp), color = Color.Black
        )
    }
}












