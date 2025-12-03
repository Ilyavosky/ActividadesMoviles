package com.ilya.practica4

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ilya.practica4.ui.theme.Practica4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CameraScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var showFullImage by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            capturedImage = imageBitmap
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (capturedImage != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clickable { showFullImage = true },
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Image(
                    bitmap = capturedImage!!.asImageBitmap(),
                    contentDescription = "Imagen capturada",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Da un Tap para ver la imagen",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Card(
                modifier = Modifier
                    .size(300.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No se ha capturado una imagen aún",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(context.packageManager) != null) {
                    cameraLauncher.launch(intent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (capturedImage == null) "Abrir la camara" else "Toma otra foto")
        }
    }

    if (showFullImage && capturedImage != null) {
        FullScreenImageDialog(
            bitmap = capturedImage!!,
            onDismiss = { showFullImage = false }
        )
    }
}

@Composable
fun FullScreenImageDialog(
    bitmap: Bitmap,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen en pantalla completa",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onDismiss() },
                    contentScale = ContentScale.Fit
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar la pantalla completa",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}