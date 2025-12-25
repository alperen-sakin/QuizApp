package com.example.quizapp.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class QuizResult(
    val userId: String = "",
    val userName: String = "",
    val categoryName: String = "",

    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val score: Int = 0,

    @get:ServerTimestamp
    val completedAt: Date? = null
)
