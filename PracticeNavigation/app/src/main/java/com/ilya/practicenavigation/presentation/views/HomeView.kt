package com.ilya.practicenavigation.presentation.views


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilya.practicenavigation.data.Student
import com.ilya.practicenavigation.data.students


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Dashboard")
                }
            )
        }
    ) {
        Content(it, students, navController)
    }
}

@Composable
fun Content(paddingValues: PaddingValues, students: List<Student>, navController: NavController){
    LazyColumn (
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 10.dp)
    ) {
        items(students){ student ->
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("Details/${student.id}/${student.name}/${student.description}")
                    }
                    .size(150.dp)
                    .background(color = Color.Yellow, CircleShape)
                    .wrapContentSize(Alignment.Center)
            ){
                Text(student.name, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}