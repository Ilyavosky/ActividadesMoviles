package com.ilya.students_unidad3.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilya.students_unidad3.viewmodel.StudentViewModel
import com.ilya.students_unidad3.data.Students
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(viewModel: StudentViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Student Statistics") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StatisticCard(
                    title = "Average Score",
                    value = String.format(Locale.US, "%.2f", uiState.averageScore)
                )
            }

            item {
                val lowestStudent = uiState.lowestScoringStudent
                val value = if (lowestStudent != null) {
                    "${lowestStudent.name} ${lowestStudent.lastName} (Score: ${lowestStudent.score})"
                } else {
                    "N/A"
                }
                StatisticCard(
                    title = "Lowest Scoring Student (Rezago)",
                    value = value
                )
            }

            item {
                Column {
                    Text(
                        text = "Top 3 Students by Group",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (uiState.top3ByGroup.isEmpty()) {
                        Text("No data available.")
                    } else {
                        uiState.top3ByGroup.forEach { (group, students) ->
                            GroupTop3Card(group = group, students = students)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun GroupTop3Card(group: String, students: List<Students>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Group: $group",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            students.forEachIndexed { index, student ->
                Text(
                    text = "${index + 1}. ${student.name} ${student.lastName} (Score: ${student.score})"
                )
            }
        }
    }
}