package com.example.quizapp.data.mapper

import com.example.quizapp.data.dto.QuestionDto
import com.example.quizapp.domain.model.Question

fun QuestionDto.toDomain(
    id: String
): Question {
    return Question(
        id = id,
        categoryID = categoryID ?: "",
        text = text ?: "",
        options = options ?: emptyList(),
        answer = answer ?: ""
    )
}
