package com.example.quizapp.domain.model

data class Question(
    val id: String,
    val categoryID: String,
    val text: String,
    val options: List<String>,
    val answer: String,
)
