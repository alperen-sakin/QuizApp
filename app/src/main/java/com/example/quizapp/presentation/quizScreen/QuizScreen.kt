package com.example.quizapp.presentation.quizScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quizapp.domain.model.Question
import com.example.quizapp.presentation.quizScreen.components.ExitConfirmationDialog
import com.example.quizapp.presentation.quizScreen.components.QuestionSection
import com.example.quizapp.presentation.quizScreen.components.QuestionSectionState
import com.example.quizapp.presentation.quizScreen.components.QuizContentState
import com.example.quizapp.presentation.quizScreen.components.QuizTimer
import com.example.quizapp.presentation.quizScreen.components.QuizTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    navController: NavController,
    questions: List<Question>,
    viewModel: QuizViewModel = hiltViewModel(),

) {
    var showDialog by remember { mutableStateOf(false) }
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()

    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsStateWithLifecycle()
    val selectedOption by viewModel.selectedOption.collectAsStateWithLifecycle()

    Efects(viewModel, navController)

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

    if (questions.isNotEmpty()) {
        val currentQuestion = questions[currentQuestionIndex]

        Scaffold(
            topBar = {
                QuizTopAppBar(
                    totalQuestions = questions.size,
                    currentQuestion = currentQuestionIndex + 1,
                    onGoHomeClick = {
                        showDialog = true
                    }
                )
            }
        ) {
            QuizContent(
                quizContentState = QuizContentState(
                    paddingValues = it,
                    timerState = timerState,
                    currentQuestion = currentQuestion,
                    selectedOption = selectedOption,
                    currentQuestionIndex = currentQuestionIndex,
                    questions = questions
                ),
                viewModel
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun Efects(
    viewModel: QuizViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is QuizNavigationEvent.NavigateToResult -> {
                    navController.navigate("result/${event.score}/${event.timeIsOver}") {
                        popUpTo("quiz/{categoryId}") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizContent(
    quizContentState: QuizContentState,
    viewModel: QuizViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(quizContentState.paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizTimer(
            totalTime = quizContentState.timerState.totalTime,
            currentTime = quizContentState.timerState.currentTime
        )

        QuestionSection(
            questionSectionStates = QuestionSectionState(
                question = quizContentState.currentQuestion,
                selectedOption = quizContentState.selectedOption,
                isLastQuestion = quizContentState.currentQuestionIndex == quizContentState.questions.size - 1
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            onOptionSelected = { questionId, option ->
                viewModel.onOptionSelected(questionId = questionId, option = option)
            },
            onNextClick = viewModel::onNextClick,

        )
    }
}
