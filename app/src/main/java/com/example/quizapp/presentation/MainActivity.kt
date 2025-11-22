package com.example.quizapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.navigation.NavigationHost
import com.example.quizapp.presentation.signIn.GoogleAuthUiClient
import com.example.quizapp.presentation.signIn.SignInViewModel
import com.example.quizapp.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val context = LocalContext.current

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                    viewModel.resetState()
                }
            }

            QuizAppTheme {
                val isSignedIn = googleAuthUiClient.getSignedInUser() != null
                val startDestination = if (isSignedIn) "home" else "login"

                NavigationHost(
                    navController = navController,
                    startDestination = startDestination,
                    onSignInClick = {
                        lifecycleScope.launch {
                            try {
                                val response = googleAuthUiClient.signIn()
                                if (response != null) {
                                    viewModel.processSignInResponse(response)
                                }
                            } catch (e: NoCredentialException) {
                                Toast.makeText(
                                    context,
                                    "Giriş yapılacak Google hesabı bulunamadı.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                e.printStackTrace()
                            }
                        }
                    }
                )
            }
        }
    }
}
