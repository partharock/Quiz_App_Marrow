package com.example.mcqquiz

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val answerIndex: Int
)
