package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.presentation.homeScreen.HomeRoute
import com.example.quizapp.presentation.quizScreen.QuizScreen
import com.example.quizapp.presentation.quizScreen.QuizViewModel
import com.example.quizapp.presentation.resultScreen.ResultScreen
import com.example.quizapp.presentation.signIn.SignInViewModel
import com.example.quizapp.presentation.signIn.screen.SignInScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: String,
    onSignInClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeRoute(navController = navController)
        }
        composable("login") {
            val viewModel: SignInViewModel = hiltViewModel()

            val state by viewModel.state.collectAsStateWithLifecycle()
            SignInScreen(
                state = state,
                onSignInClick = onSignInClick,
                onSignInSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = "quiz/{categoryId}") { backStackEntry ->

            val viewModel: QuizViewModel = hiltViewModel()
            val questions by viewModel.questions.collectAsStateWithLifecycle()
            val categoryId = backStackEntry.arguments?.getString("categoryId")

            LaunchedEffect(categoryId) {
                if (categoryId != null) {
                    viewModel.loadQuestions(categoryId)
                }
            }

            QuizScreen(
                navController = navController,
                questions = questions,
                viewModel = viewModel
            )
        }

        composable(route = "result/{score}/{timeIsOver}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")
            val timeIsOver = backStackEntry.arguments?.getString("timeIsOver")

            ResultScreen(
                score = score,
                timeIsOver = timeIsOver,
                // navController = navController
            )
        }
    }
}
