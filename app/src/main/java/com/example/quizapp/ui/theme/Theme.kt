package com.example.quizapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MyColorScheme = lightColorScheme(
    primary = Maroon,
    secondary = Maroon,
    background = BackgroundColor,
    surface = QueenPink,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black

)

@Composable
fun QuizAppTheme(

    content: @Composable () -> Unit
) {
    val colorScheme = MyColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
