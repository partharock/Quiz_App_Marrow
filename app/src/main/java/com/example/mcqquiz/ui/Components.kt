package com.example.mcqquiz.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OptionRow(text: String, isSelected: Boolean, isCorrect: Boolean?, onClick: () -> Unit) {
    // if isCorrect is null => not revealed yet
    val bg = when {
        isCorrect == null && isSelected -> Color.LightGray
        isCorrect == true -> Color(0xFFB9F6CA) // greenish
        isCorrect == false -> Color(0xFFFFCDD2) // reddish
        else -> Color(0xFFF5F5F5)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(bg, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}
