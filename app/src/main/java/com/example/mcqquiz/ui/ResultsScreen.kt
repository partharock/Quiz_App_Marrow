package com.example.mcqquiz.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultsScreen(
    total: Int,
    correct: Int,
    skipped: Int,
    longestStreak: Int,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Results", style = androidx.compose.material.MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Correct: $correct / $total")
        Text("Skipped: $skipped")
        Text("Longest Streak: $longestStreak")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRestart) {
            Text("Restart Quiz")
        }
    }
}
