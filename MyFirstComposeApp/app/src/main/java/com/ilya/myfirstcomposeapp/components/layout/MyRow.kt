package com.ilya.myfirstcomposeapp.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyRow(modifier: Modifier){
    Row(modifier = modifier.fillMaxSize().horizontalScroll(rememberScrollState())){
        Text("Hola 1", modifier = Modifier.background(Color.Red).weight(1f))
        Text("Hola 2", modifier = Modifier.background(Color.Blue).weight(1f))
        Text("Hola 3", modifier = Modifier.background(Color.Cyan).weight(1f))
        Text("Hola 1", modifier = Modifier.background(Color.Red).weight(1f))
        Text("Hola 2", modifier = Modifier.background(Color.Blue).weight(1f))
        Text("Hola 3", modifier = Modifier.background(Color.Cyan).weight(1f))
        Text("Hola 1", modifier = Modifier.background(Color.Red).weight(1f))
        Text("Hola 2", modifier = Modifier.background(Color.Blue).weight(1f))
        Text("Hola 3", modifier = Modifier.background(Color.Cyan).weight(1f))
        Text("Hola 1", modifier = Modifier.background(Color.Red).weight(1f))
        Text("Hola 2", modifier = Modifier.background(Color.Blue).weight(1f))
        Text("Hola 3", modifier = Modifier.background(Color.Cyan).weight(1f))
        Text("Hola 1", modifier = Modifier.background(Color.Red).weight(1f))
        Text("Hola 2", modifier = Modifier.background(Color.Blue).weight(1f))
        Text("Hola 3", modifier = Modifier.background(Color.Cyan).weight(1f))
    }
}