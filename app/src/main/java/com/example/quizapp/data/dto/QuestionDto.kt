package com.example.quizapp.data.dto

data class QuestionDto(
    val categoryID: String? = null,
    val text: String? = null,
    val options: List<String>? = null,
    val answer: String? = null
)
