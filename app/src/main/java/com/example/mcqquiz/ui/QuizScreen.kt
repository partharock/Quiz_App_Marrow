package com.example.mcqquiz.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mcqquiz.Question
import com.example.mcqquiz.UiState

@Composable
fun QuizScreen(state: UiState, onSelect: (Int) -> Unit, onSkip: () -> Unit) {
    val q: Question = state.questions[state.currentIndex]
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Quiz", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(12.dp))

        // Streak indicators
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔥",
                modifier = Modifier.alpha(if (state.streak >= 3) 1f else 0.2f).padding(horizontal = 2.dp),
                fontSize = 24.sp
            )
            Text(
                text = "🔥",
                modifier = Modifier.alpha(if (state.streak >= 4) 1f else 0.2f).padding(horizontal = 2.dp),
                fontSize = 24.sp
            )
            Text(
                text = "🔥",
                modifier = Modifier.alpha(if (state.streak >= 5) 1f else 0.2f).padding(horizontal = 2.dp),
                fontSize = 24.sp
            )
            Text(
                text = "🔥",
                modifier = Modifier.alpha(if (state.streak >= 6) 1f else 0.2f).padding(horizontal = 2.dp),
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${state.streak} questions streak achieved !!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(if (state.streak >= 3) 1f else 0f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Next in ${state.remainingTime}s",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(if (state.revealed) 1f else 0f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Question ${state.currentIndex + 1} of ${state.totalQuestions}",
            style = MaterialTheme.typography.bodyLarge
        )
        LinearProgressIndicator(
            progress = { (state.currentIndex + 1) / state.totalQuestions.toFloat() },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground,
            trackColor = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.height(90.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                q.question,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        q.options.forEachIndexed { idx, opt ->
            val isSelected = state.selectedIndex == idx
            val isCorrect = if (!state.revealed) null else (idx == q.answerIndex)
            OptionRow(text = opt, isSelected = isSelected, isCorrect = isCorrect) {
                if (!state.revealed) onSelect(idx)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSkip, modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(Icons.Default.SkipNext, contentDescription = "Skip")
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (state.revealed) "Next" else "Skip")
        }
    }
}
