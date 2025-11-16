package com.example.mcqquiz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Tests the primary user flow: starting the app, clicking "Play Quiz",
     * and verifying that the first question screen is displayed.
     */
    @Test
    fun test_startQuiz_and_seeFirstQuestion() {
        // Start the quiz
        composeTestRule.onNodeWithText("Play Quiz").performClick()

        // After a moment for loading, the first question should be visible.
        // We look for a node containing "Question 1 of" to confirm.
        composeTestRule.onNodeWithText("Question 1 of", substring = true).assertIsDisplayed()
    }
}
