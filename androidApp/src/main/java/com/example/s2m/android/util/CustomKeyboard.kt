package com.example.s2m.android.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.s2m.android.R
import com.example.s2m.android.view.TextFieldType

@Composable
fun CustomKeyboard1(
    activeTextField: TextFieldType,
    onKeyPressed: (String, TextFieldType) -> Unit,
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

@Composable
fun KeyboardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        border= BorderStroke(1.dp,Color.Black),
        colors= ButtonDefaults.buttonColors(backgroundColor = Color(0xffE5FBFE)),
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