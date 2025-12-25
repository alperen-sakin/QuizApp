package com.example.quizapp.presentation.quizScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.ui.theme.MontserratFamily
import com.example.quizapp.ui.theme.SelectedColor
import com.example.quizapp.ui.theme.SelectedContainerColor

@Composable
fun QuestionSection(
    questionSectionStates: QuestionSectionState,
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit = {},
    onOptionSelected: (questionId: String, option: String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        QuestionCard(questionSectionStates)
        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                questionSectionStates.question.options,
            ) { option ->

                OptionBox(
                    option,
                    isOptionSelected = option == questionSectionStates.selectedOption,
                    onOptionSelected = {
                        onOptionSelected(questionSectionStates.question.id, option)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        val isNextEnabled = questionSectionStates.selectedOption != null

        NextButton(onNextClick, isNextEnabled, questionSectionStates)

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun QuestionCard(questionSectionStates: QuestionSectionState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = questionSectionStates.question.text,
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NextButton(
    onNextClick: () -> Unit,
    isNextEnabled: Boolean,
    questionSectionStates: QuestionSectionState
) {
    Button(
        onClick = onNextClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        enabled = isNextEnabled

    ) {
        Text(
            text = if (questionSectionStates.isLastQuestion) "Finish" else "Next",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun OptionBox(
    option: String,
    isOptionSelected: Boolean,
    onOptionSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isOptionSelected) {
                SelectedContainerColor
            } else {
                Color.White
            }
        ),
        onClick = { onOptionSelected(option) }

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = option,
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterVertically),
            )

            if (isOptionSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = SelectedColor,
                )
            }
        }
    }
}
