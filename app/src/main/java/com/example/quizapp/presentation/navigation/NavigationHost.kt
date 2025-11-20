package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.presentation.homeScreen.HomeRoute
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
    }
}
