package com.ilya.mynavigation.presentation.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ilya.mynavigation.presentation.components.details.home.TopCenterBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(navController: NavController){
    Scaffold (
        topBar = {
            TopCenterBar()
        }

    ) {
        Content(it)
    }
}

@Composable
fun Content(PaddingValues: PaddingValues){
    Column(
        modifier = Modifier.padding(PaddingValues)
    ){
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(Color.White, CircleShape)
                .wrapContentSize(Alignment.Center)
                .clickable{
                    navController.navigate("Details")
                }
        ){
            Text("Hola")
        }
    }
}