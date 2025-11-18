package com.ilya.students_unidad3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomBarScreen(
        route = "dashboard",
        title = "Dashboard",
        icon = Icons.Default.Dashboard
    )

    object Statistics : BottomBarScreen(
        route = "statistics",
        title = "Statistics",
        icon = Icons.Default.Calculate
    )
}