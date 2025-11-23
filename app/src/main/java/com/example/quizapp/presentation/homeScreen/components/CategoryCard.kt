package com.example.quizapp.presentation.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.quizapp.R
import com.example.quizapp.domain.model.Category
import com.example.quizapp.ui.theme.MontserratFamily
import dagger.hilt.android.EntryPointAccessors

@Composable
fun CategoryCard(
    category: Category,
    onCategoryClick: (Category) -> Unit
) {
    val imageLoader = EntryPointAccessors.fromApplication(
        LocalContext.current,
        CoilEntryPoint::class.java
    ).imageLoader()

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = category.color
        ),
        onClick = { onCategoryClick(category) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = category.icon,
                    imageLoader = imageLoader,
                    contentDescription = category.name,
                    modifier = Modifier.padding(16.dp),
                    placeholder = painterResource(id = R.drawable.category_general_icon),
                    error = painterResource(id = R.drawable.category_general_icon),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center,

            ) {
                Text(
                    text = category.name,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = MontserratFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@dagger.hilt.EntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
interface CoilEntryPoint {
    fun imageLoader(): ImageLoader
}
