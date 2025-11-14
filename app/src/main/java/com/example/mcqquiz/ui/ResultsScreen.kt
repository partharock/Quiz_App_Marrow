package com.example.mcqquiz.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.mcqquiz.ui.theme.RestartButtonColor

@Composable
fun ResultsScreen(
    total: Int,
    correct: Int,
    skipped: Int,
    longestStreak: Int,
    onRestart: () -> Unit
) {
    val visible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible.value = true
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible.value,
            enter = fadeIn(animationSpec = tween(durationMillis = 500))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Results", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Correct: $correct / $total", style = MaterialTheme.typography.bodyLarge)
                Text("Skipped: $skipped", style = MaterialTheme.typography.bodyLarge)
                Text("Longest Streak: $longestStreak", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onRestart,
                    modifier = Modifier.scale(scale).clickable(interactionSource = interactionSource, indication = null) { onRestart() },
                    colors = ButtonDefaults.buttonColors(containerColor = RestartButtonColor)
                ) {
                    Text("Restart Quiz")
                }
            }
        }
    }
}
