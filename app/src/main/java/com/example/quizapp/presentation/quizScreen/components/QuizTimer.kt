package com.example.quizapp.presentation.quizScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.ui.theme.Maroon

@Composable
fun QuizTimer(
    modifier: Modifier = Modifier,
    totalTime: Long,
    currentTime: Long,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(80.dp)
    ) {
        val progress = (currentTime.toFloat() / totalTime.toFloat())

        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(80.dp),
            color = Maroon,
            strokeWidth = 6.dp,
            strokeCap = StrokeCap.Round
        )

        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(80.dp),
            color = Maroon,
            strokeWidth = 6.dp,
            strokeCap = StrokeCap.Round
        )

        Text(
            text = (currentTime / 1000).toString(),
            color = Maroon,
            fontSize = 30.sp,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
