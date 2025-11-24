package com.example.quizapp.presentation.quizScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.quizScreen.components.ExitConfirmationDialog
import com.example.quizapp.presentation.quizScreen.components.QuizTopAppBar
import com.example.quizapp.ui.theme.QuizAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    navController: NavController,

) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ExitConfirmationDialog(
            onConfirm = {
                showDialog = false
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            QuizTopAppBar(
                totalQuestions = 10,
                currentQuestion = 1,
                onGoHomeClick = {
                    showDialog = true
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
        }
    }
}

@Preview
@Composable
fun QuizScreenPreview() {
    QuizAppTheme {
        QuizScreen(
            navController = rememberNavController(),

        )
    }
}
