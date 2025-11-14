package com.example.mcqquiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

data class UiState(
    val loading: Boolean = true,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val revealed: Boolean = false,
    val correctAnswers: Int = 0,
    val skippedAnswers: Int = 0,
    val streak: Int = 0,
    val longestStreak: Int = 0,
    val showResults: Boolean = false,
    val totalQuestions: Int = 0,
    val remainingTime: Int = 5
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var advanceJob: Job? = null

    companion object {
        private const val ADVANCE_DELAY_SECONDS = 2
    }

    init {
        viewModelScope.launch {
            loadQuestions()
        }
    }

    private fun getAdvanceJob(): Job {
        return viewModelScope.launch {
            for (i in ADVANCE_DELAY_SECONDS downTo 1) {
                _uiState.value = _uiState.value.copy(remainingTime = i)
                delay(1000)
            }
            advance()
        }
    }

    private suspend fun loadQuestions() {
        val ctx = getApplication<Application>().applicationContext
        val isr = InputStreamReader(ctx.assets.open("questions.json"))
        val br = BufferedReader(isr)
        val text = br.use { it.readText() }
        val arr = JSONArray(text)
        val list = mutableListOf<Question>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val optionsArr = obj.getJSONArray("options")
            val opts = (0 until optionsArr.length()).map { optionsArr.getString(it) }
            list.add(
                Question(
                    id = obj.optInt("id", i),
                    question = obj.getString("question"),
                    options = opts,
                    answerIndex = obj.getInt("answerIndex")
                )
            )
        }
        _uiState.value = _uiState.value.copy(
            loading = false,
            questions = list,
            totalQuestions = list.size,
            remainingTime = ADVANCE_DELAY_SECONDS
        )
    }

    fun selectAnswer(idx: Int) {
        if (_uiState.value.revealed) return
        val s = _uiState.value
        val q = s.questions.getOrNull(s.currentIndex) ?: return
        val correct = (idx == q.answerIndex)
        var newStreak = s.streak
        var longest = s.longestStreak
        var correctCount = s.correctAnswers
        if (correct) {
            newStreak += 1
            correctCount += 1
            if (newStreak > longest) longest = newStreak
        } else {
            newStreak = 0
        }

        _uiState.value = s.copy(
            selectedIndex = idx,
            revealed = true,
            streak = newStreak,
            longestStreak = longest,
            correctAnswers = correctCount
        )

        advanceJob?.cancel()
        advanceJob = getAdvanceJob()
    }

    fun skip() {

        advanceJob?.cancel()

        val s = _uiState.value
        if (s.revealed) {
            // if already revealed, advance immediately
            advance()
            return
        }
        _uiState.value = s.copy(skippedAnswers = s.skippedAnswers + 1)
        advance()
    }

    private fun advance() {
        val s = _uiState.value
        val next = s.currentIndex + 1
        if (next >= s.questions.size) {
            _uiState.value = s.copy(showResults = true)
        } else {
            _uiState.value = s.copy(
                currentIndex = next,
                selectedIndex = null,
                revealed = false,
                remainingTime = ADVANCE_DELAY_SECONDS
            )
        }
    }

    fun restart() {
        _uiState.value = UiState(
            loading = false,
            questions = _uiState.value.questions,
            totalQuestions = _uiState.value.questions.size,
            remainingTime = ADVANCE_DELAY_SECONDS
        )
    }
}
