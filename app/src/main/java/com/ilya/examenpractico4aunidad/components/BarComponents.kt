package com.ilya.examenpractico4aunidad.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ilya.examenpractico4aunidad.shared.MainIconButton

@Composable
fun TitleBar(name: String) {
    Text(name, fontSize = 25.sp, color = MaterialTheme.colorScheme.onSurface)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CenterAppBar(
    name: String,
    containerColor: Color,
    onNavigationClick: (() -> Unit)? = null,
    onActionButtonClick: (() -> Unit)? = null,
    actionIcon: (@Composable RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = { TitleBar(name) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        ),
        navigationIcon = {
            if (onNavigationClick != null) {
                MainIconButton(
                    onClick = onNavigationClick,
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            if (actionIcon != null) {
                actionIcon()
            } else if (onActionButtonClick != null) {
                MainIconButton(
                    onClick = onActionButtonClick,
                    icon = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}