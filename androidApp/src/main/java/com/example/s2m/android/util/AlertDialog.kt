package com.example.s2m.android.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun MyAlertDialog(text: String, show: Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            modifier = Modifier
                .height(150.dp)
                .width(300.dp),
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 40.dp, top = 20.dp),
                    fontWeight = FontWeight.Bold
                )
            },
            buttons = {
                Column {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                        shape = RoundedCornerShape(50),
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .padding(start = 50.dp, top = 60.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "OK",
                            color = Color.White,
                        )
                    }
                }
            }
        )
    }
}

