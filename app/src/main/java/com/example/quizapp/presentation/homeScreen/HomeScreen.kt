package com.example.quizapp.presentation.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.domain.model.Category
import com.example.quizapp.presentation.common.HALF_SIZE
import com.example.quizapp.presentation.homeScreen.components.ContentSection
import com.example.quizapp.presentation.homeScreen.components.HomeHeader
import com.example.quizapp.presentation.homeScreen.components.TopAppBar
import com.example.quizapp.presentation.signIn.UserData
import com.example.quizapp.ui.theme.QuizAppTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(onSignOut)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(HALF_SIZE),
                userName = userData?.username ?: "",
                profilePicture = userData?.profilePictureUrl
            )

            ContentSection(
                categories = categories,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
fun HomeRoute(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val userData = viewModel.getSignedInUser()
    val coroutineScope = rememberCoroutineScope()

    val categories by viewModel.categories.collectAsStateWithLifecycle()

    HomeScreen(
        categories = categories,
        userData = userData,
        onSignOut = {
            coroutineScope.launch {
                viewModel.signOut()
                navController.navigate("login") {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        },
        onCategoryClick = { categoryId ->
            navController.navigate("quiz/$categoryId")
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()

    QuizAppTheme {
        HomeScreen(
            userData = UserData(
                userId = "12345",
                username = "Test User",
                profilePictureUrl = null
            ),
            onSignOut = {},
            categories = emptyList(),
            onCategoryClick = { categoryId ->
                navController.navigate("quiz/$categoryId")
            }
        )
    }
}
