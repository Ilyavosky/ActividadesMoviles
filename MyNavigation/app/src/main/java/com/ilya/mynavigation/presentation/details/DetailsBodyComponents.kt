package com.ilya.mynavigation.presentation.details

import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopCenterBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text("Details")
        },
        navigationIcon = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(imageVector = Icons.AutoMirrored)
            }
        }
    )
}