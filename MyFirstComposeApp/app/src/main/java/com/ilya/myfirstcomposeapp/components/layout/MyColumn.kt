package com.ilya.myfirstcomposeapp.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
//@Preview(showBackground = true)
fun MyColumn(modifier: Modifier = Modifier){

    /*Column(modifier = modifier){
        Text("Hola 1", modifier = Modifier.background(Color.Red).weight(1f))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan). weight(1f))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow).weight(1f))
        Text("Hola 4", modifier = Modifier.background(Color.Blue).weight(1f))
    */

    //SpaceAround, SpaceEvenly, SpaceBetween, CenterHorizontally, horizontalAlignment,
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))
        Text("Hola 2", modifier = Modifier.background(Color.Cyan))
        Text("Hola 3", modifier = Modifier.background(Color.Yellow))
        Text("Hola 4", modifier = Modifier.background(Color.Blue))
        Text("Hola 1", modifier = Modifier.background(Color.Red))



    }
}