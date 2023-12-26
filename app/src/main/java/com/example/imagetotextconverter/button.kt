package com.example.imagetotextconverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(width = 800.dp,height = 80.dp)
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .background(
                color = Color(0xFF7058D1).copy(alpha = 0.63f),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Text(text = text, color = Color.White)
    }
}



