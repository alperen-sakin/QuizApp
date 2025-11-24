package com.example.quizapp.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.quizapp.data.dto.CategoryDto
import com.example.quizapp.domain.model.Category

fun CategoryDto.toDomain(
    id: String
): Category {
    return Category(
        id = id,
        name = this.name ?: "Unknown",
        color = try {
            Color((this.color ?: "#000000").toColorInt())
        } catch (e: Exception) {
            e.printStackTrace()
            Color.Black
        },
        icon = this.iconUrl ?: "",

    )
}
