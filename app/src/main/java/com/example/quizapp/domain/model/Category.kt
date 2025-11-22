package com.example.quizapp.domain.model

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class Category(
    val id: Int = Random.nextInt(),
    val name: String,
    val color: Color,
    val icon: Int
)
