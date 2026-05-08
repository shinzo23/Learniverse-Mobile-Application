// QuizQuestion.kt
package com.example.demo_study_app

data class QuizQuestion(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)