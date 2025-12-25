package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.Category
import com.example.quizapp.domain.model.Question

interface QuizRepository {

    suspend fun getCategories(): Result<List<Category>>
    suspend fun getQuestions(categoryID: String): Result<List<Question>>
}
