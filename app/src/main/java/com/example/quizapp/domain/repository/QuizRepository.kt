package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.Category

interface QuizRepository {

    suspend fun getCategories(): Result<List<Category>>
}
