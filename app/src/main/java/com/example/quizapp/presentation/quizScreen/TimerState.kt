package com.example.quizapp.presentation.quizScreen

data class TimerState(
    val totalTime: Long = 3000L,
    val currentTime: Long = totalTime,
)
