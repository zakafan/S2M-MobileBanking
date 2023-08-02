package com.example.s2m.android.util

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton1(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: Int
) {
    Column(
        modifier = modifier
            .width(72.dp)
            .height(100.dp),
    ) {
        Button(
            shape = RoundedCornerShape(20),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff112D4E)),
            modifier = modifier
                .width(80.dp)
                .height(75.dp)
        ){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Icon/Wallet",
                tint = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Visible
            )
        }
    }
}