package com.example.quizapp.data.mapper

import androidx.compose.ui.graphics.Color
import com.example.quizapp.data.dto.CategoryDto
import com.example.quizapp.domain.model.Category

fun CategoryDto.toDomain(
    id: String
): Category {
    return Category(
        id = id,
        name = this.name ?: "Unknown",
        color = try {
            Color(android.graphics.Color.parseColor(this.categoryColor ?: "#000000"))
        } catch (e: Exception) {
            e.printStackTrace()
            Color.Black
        },
        icon = this.categoryIcon ?: "",

    )
}
