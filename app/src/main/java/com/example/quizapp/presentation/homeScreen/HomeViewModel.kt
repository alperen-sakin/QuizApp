package com.example.quizapp.presentation.homeScreen

import androidx.lifecycle.ViewModel
import com.example.quizapp.presentation.signIn.GoogleAuthUiClient
import com.example.quizapp.presentation.signIn.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    fun getSignedInUser(): UserData? {
        return googleAuthUiClient.getSignedInUser()
    }

    suspend fun signOut() {
        googleAuthUiClient.signOut()
    }
}
