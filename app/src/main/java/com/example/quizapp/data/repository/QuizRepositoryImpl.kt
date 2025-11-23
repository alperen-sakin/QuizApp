package com.example.quizapp.data.repository

import com.example.quizapp.data.dto.CategoryDto
import com.example.quizapp.data.mapper.toDomain
import com.example.quizapp.domain.model.Category
import com.example.quizapp.domain.repository.QuizRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : QuizRepository {
    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val snapshot = firestore.collection("categories").get().await()
            val categories = snapshot.documents.mapNotNull { doc ->
                val categoryDto = doc.toObject(CategoryDto::class.java)
                categoryDto?.toDomain(doc.id)
            }
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
