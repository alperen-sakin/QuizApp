package com.example.quizapp.data.mapper

import com.example.quizapp.data.dto.QuizResultDto
import com.example.quizapp.domain.model.QuizResult

fun QuizResultDto.toQuizResult(): QuizResult {
    return QuizResult(
        userId = this.userId,
        userName = this.userName,
        categoryName = this.categoryName,
        correctAnswers = this.correctAnswers,
        wrongAnswers = this.wrongAnswers,
        totalQuestions = this.totalQuestions,
        score = this.score,
        completedAt = this.completedAt
    )
}

fun QuizResult.toQuizResultDto(): QuizResultDto {
    return QuizResultDto(
        userId = this.userId,
        userName = this.userName,
        categoryName = this.categoryName,
        correctAnswers = this.correctAnswers,
        wrongAnswers = this.wrongAnswers,
        totalQuestions = this.totalQuestions,
        score = this.score,
        completedAt = this.completedAt
    )
}
