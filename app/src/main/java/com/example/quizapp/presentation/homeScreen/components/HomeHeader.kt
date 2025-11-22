package com.example.quizapp.presentation.homeScreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.quizapp.R
import com.example.quizapp.presentation.common.BEND_RATIO
import com.example.quizapp.ui.theme.Maroon
import com.example.quizapp.ui.theme.QueenPink

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    userName: String = "User",
    profilePicture: String?
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeHeaderBackground()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = profilePicture,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.azapkaan_logo),
                placeholder = painterResource(R.drawable.azapkaan_logo)
            )

            Spacer(Modifier.height(17.dp))

            Text(
                text = "Welcome $userName",
                style = MaterialTheme.typography.titleLarge,
                color = QueenPink
            )
        }
    }
}

@Composable
fun HomeHeaderBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(0f, 0f)

            lineTo(size.width, 0f)
            lineTo(size.width, size.height)

            quadraticTo(
                size.width / 2,
                size.height * BEND_RATIO,
                0f,
                size.height
            )
        }

        drawPath(path, color = Maroon)
    }
}
