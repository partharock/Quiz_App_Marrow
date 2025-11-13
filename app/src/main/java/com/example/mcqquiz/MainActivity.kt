package com.example.mcqquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.mcqquiz.ui.LoadingScreen
import com.example.mcqquiz.ui.QuizScreen
import com.example.mcqquiz.ui.ResultsScreen

class MainActivity : ComponentActivity() {
    private val vm: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by vm.uiState.collectAsState()
            Surface(modifier = Modifier.fillMaxSize()) {
                when {
                    state.loading -> LoadingScreen()
                    state.showResults -> ResultsScreen(
                        total = state.totalQuestions,
                        correct = state.correctAnswers,
                        skipped = state.skippedAnswers,
                        longestStreak = state.longestStreak,
                        onRestart = { vm.restart() }
                    )

                    else -> QuizScreen(
                        state = state,
                        onSelect = { idx -> vm.selectAnswer(idx) },
                        onSkip = { vm.skip() }
                    )
                }
            }
        }
    }
}
