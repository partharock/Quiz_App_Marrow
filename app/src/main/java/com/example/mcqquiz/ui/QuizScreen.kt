package com.example.mcqquiz.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.mcqquiz.Question
import com.example.mcqquiz.UiState

@Composable
fun QuizScreen(state: UiState, onSelect: (Int) -> Unit, onSkip: () -> Unit) {
    val q: Question = state.questions[state.currentIndex]
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Q ${state.currentIndex + 1}/${state.totalQuestions}",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.weight(1f))

            if (state.revealed) {
                Text(text = "Next in ${state.remainingTime}s", style = MaterialTheme.typography.subtitle2)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Streak badge
            val lit = state.streak >= 3
            val scale = animateFloatAsState(if (lit) 1.2f else 1f)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🔥", modifier = Modifier.scale(scale.value))
            }
        }

        Text(
            text = "${state.streak} questions streak achieved !!",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(if (state.streak > 1) 1f else 0f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(q.question, style = MaterialTheme.typography.h6, modifier = Modifier.padding(8.dp))

        Spacer(modifier = Modifier.height(12.dp))

        q.options.forEachIndexed { idx, opt ->
            val isSelected = state.selectedIndex == idx
            val isCorrect = if (!state.revealed) null else (idx == q.answerIndex)
            OptionRow(text = opt, isSelected = isSelected, isCorrect = isCorrect) {
                if (!state.revealed) onSelect(idx)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = onSkip) {
                Icon(Icons.Default.SkipNext, contentDescription = "Skip")
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (state.revealed) "Next" else "Skip")
            }
        }
    }
}
