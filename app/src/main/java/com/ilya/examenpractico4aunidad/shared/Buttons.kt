package com.ilya.examenpractico4aunidad.shared

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MainIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    tint: Color
) {
    IconButton(onClick = onClick) {
        Icon(icon, contentDescription = "Icon Button", tint = tint)
    }
}