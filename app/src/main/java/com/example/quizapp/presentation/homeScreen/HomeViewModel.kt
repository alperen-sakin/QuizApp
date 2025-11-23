package com.example.quizapp.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.model.Category
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.presentation.signIn.GoogleAuthUiClient
import com.example.quizapp.presentation.signIn.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val result = quizRepository.getCategories()
            result.onSuccess { categories ->
                _categories.value = categories
            }.onFailure {
                _categories.value = emptyList()
            }
        }
    }

    fun getSignedInUser(): UserData? {
        return googleAuthUiClient.getSignedInUser()
    }

    suspend fun signOut() {
        googleAuthUiClient.signOut()
    }
}
