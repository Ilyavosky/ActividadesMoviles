package com.ilya.myfirstcomposeapp.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MyBox() {
    Box(modifier = Modifier.fillMaxSize(),contentAlignment= Alignment.Center){
        Box(modifier = Modifier
            .size(200.dp)
        .background(Color.Yellow)
        .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ){
            Text(" And your skin, oh yeah, your skin, and bones\n" +
                    "Turn into something beautiful\n" +
                    "And you know, for you, I'd bleed myself dry\n" +
                    "For you, I'd bleed myself dry")
        }
    }
}