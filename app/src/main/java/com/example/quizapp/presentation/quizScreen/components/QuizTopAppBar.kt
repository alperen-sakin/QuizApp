package com.example.quizapp.presentation.quizScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.theme.Maroon
import com.example.quizapp.ui.theme.MontserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizTopAppBar(
    totalQuestions: Int = 10,
    currentQuestion: Int = 1,
    onGoHomeClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "$currentQuestion/$totalQuestions",
                color = Maroon,
                fontWeight = FontWeight.SemiBold,
                fontFamily = MontserratFamily
            )
        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .clickable { onGoHomeClick() },
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    tint = Maroon
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Go to Home",
                    style = TextStyle(
                        color = Maroon,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MontserratFamily
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )

    )
}
