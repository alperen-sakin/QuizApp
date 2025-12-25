package com.example.quizapp.presentation.quizScreen.components

import androidx.compose.foundation.layout.PaddingValues
import com.example.quizapp.domain.model.Question
import com.example.quizapp.presentation.quizScreen.TimerState

data class QuizContentState(
    val paddingValues: PaddingValues,
    val timerState: TimerState,
    val currentQuestion: Question,
    val selectedOption: String?,
    val currentQuestionIndex: Int,
    val questions: List<Question>,
)

data class QuestionSectionState(
    val question: Question,
    val selectedOption: String?,
    val isLastQuestion: Boolean = false
)
