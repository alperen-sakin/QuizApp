package com.example.quizapp.presentation.quizScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.model.Question
import com.example.quizapp.domain.model.QuizResult
import com.example.quizapp.domain.repository.QuizRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class QuizNavigationEvent {
    data class NavigateToResult(
        val score: Int? = null,
        val timeIsOver: String? = null
    ) : QuizNavigationEvent()
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _timerState = MutableStateFlow(TimerState())
    val timerState = _timerState.asStateFlow()

    private var timerJob: Job? = null

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _navigationEvent = Channel<QuizNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _selectedOption = MutableStateFlow<String?>(null)
    val selectedOption = _selectedOption.asStateFlow()

    private val userAnswers = mutableMapOf<String, String>()

    fun onOptionSelected(questionId: String, option: String) {
        _selectedOption.value = option
        userAnswers[questionId] = option
    }

    fun startTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            _timerState.value = TimerState()

            while (_timerState.value.currentTime > 0) {
                delay(A_SEC)

                _timerState.update { currentState ->
                    currentState.copy(
                        currentTime = currentState.currentTime - A_SEC
                    )
                }
            }
            onTimeFinished()
        }
    }

    fun onNextClick() {
        val isLastQuestion = _currentQuestionIndex.value == _questions.value.size - 1
        val isOptionSelected = _selectedOption.value != null

        if (!isOptionSelected) return

        if (isLastQuestion) {
            calculateScore()
        } else {
            _currentQuestionIndex.value++
            _selectedOption.value = null
            startTimer()
        }
    }

    private fun calculateScore() {
        viewModelScope.launch {
            var correctCount = 0
            val totalQuestions = _questions.value.size

            _questions.value.forEach { question ->
                if (userAnswers[question.id] == question.answer) {
                    correctCount++
                }
            }

            val wrongCount = totalQuestions - correctCount
            val score = correctCount * SCORE_POINT

            val currentUser = auth.currentUser ?: return@launch

            val result = QuizResult(
                userId = currentUser.uid,
                userName = currentUser.displayName ?: "Unknow Player",
                categoryName = _questions.value.firstOrNull()?.categoryID ?: "",
                correctAnswers = correctCount,
                wrongAnswers = wrongCount,
                totalQuestions = totalQuestions,
                score = score
            )

            try {
                firestore.collection("results").add(result).await()

                _navigationEvent.send(QuizNavigationEvent.NavigateToResult(score))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onTimeFinished() {
        viewModelScope.launch {
            _navigationEvent.send(QuizNavigationEvent.NavigateToResult(timeIsOver = "time is over"))
        }
    }

    fun loadQuestions(categoryID: String) {
        viewModelScope.launch {
            val result = quizRepository.getQuestions(categoryID)

            result.onSuccess { questions ->
                _questions.value = questions
                    .shuffled()
                    .take(TOTAL_QUESTIONS)
            }.onFailure {
                _questions.value = emptyList()
            }
        }
    }
}

private const val A_SEC = 100L
private const val SCORE_POINT = 10
private const val TOTAL_QUESTIONS = 10
