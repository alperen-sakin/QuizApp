package com.example.quizapp.presentation.homeScreen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.R
import com.example.quizapp.domain.model.Category
import com.example.quizapp.ui.theme.CategoryGeneralColor
import com.example.quizapp.ui.theme.CategoryGeographyColor
import com.example.quizapp.ui.theme.CategoryHistoryColor
import com.example.quizapp.ui.theme.CategoryScienceColor
import com.example.quizapp.ui.theme.CategorySportColor
import com.example.quizapp.ui.theme.CategoryTechnologyColor
import com.example.quizapp.ui.theme.IndianREd
import com.example.quizapp.ui.theme.QuizAppTheme
import com.example.quizapp.ui.theme.gradiant

@Composable
fun ContentSection() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val titles = listOf("Categories", "Leaderboard")

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            divider = {
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 3.dp
                )
            },
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(selectedTabIndex)
                        .background(brush = Brush.linearGradient(gradiant)),
                    color = Color.Transparent
                )
            }

        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(text = title)
                    },
                    selectedContentColor = IndianREd,
                    unselectedContentColor = Color.Black,

                )
            }
        }

        if (selectedTabIndex == 0) {
            CategoriesScreen()
        } else {
            Text(
                text = "Leaderboard",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun CategoriesScreen() {
    val categories = listOf(
        Category(
            name = "General",
            color = CategoryGeneralColor,
            icon = R.drawable.category_general_icon
        ),
        Category(
            name = "Sport",
            color = CategorySportColor,
            icon = R.drawable.category_sport_icon
        ),
        Category(
            name = "Science",
            color = CategoryScienceColor,
            icon = R.drawable.category_science_icon
        ),
        Category(
            name = "Technology",
            color = CategoryTechnologyColor,
            icon = R.drawable.category_technology_icon
        ),
        Category(
            name = "Geography",
            color = CategoryGeographyColor,
            icon = R.drawable.category_geography_icon
        ),
        Category(
            name = "History",
            color = CategoryHistoryColor,
            icon = R.drawable.category_history_icon
        ),

    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)

    ) {
        items(
            categories
        ) { category ->
            CategoryCard(
                category = category,
                onCategoryClick = {
                    Log.d("CategoryCard", "Category clicked: ${category.name}")
                }
            )
        }
    }
}

@Preview
@Composable
fun ContentSectionPreview() {
    QuizAppTheme {
        ContentSection()
    }
}
