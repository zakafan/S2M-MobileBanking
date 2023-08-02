package com.example.s2m.android.util

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WalletCard(balance: Double, currency: String, cardNumber: String,modifier: Modifier) {
    Card(
        backgroundColor = Color(0xff3F72AF),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(25.dp)
            .width(340.dp),
        elevation = 30.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Wallet Balance",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                text =   balance.toString() + "MAD",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                ,
                color = Color.White
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Currency",
                        style = MaterialTheme.typography.caption,
                        color = Color.White                    )
                    Text(
                        text = currency,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp),
                        color = Color.White
                    )
                }
                Column {
                    Text(
                        text = "Wallet Number",
                        style = MaterialTheme.typography.caption,
                        color = Color.White
                    )
                    Text(
                        text = cardNumber,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}