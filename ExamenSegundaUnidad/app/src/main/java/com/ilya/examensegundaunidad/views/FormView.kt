package com.ilya.examensegundaunidad.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ilya.examensegundaunidad.viewmodels.FormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormView(navController: NavController) {
    val viewModel: FormViewModel = hiltViewModel()

    var nombre by rememberSaveable { mutableStateOf("") }
    var asunto by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    val savedData by viewModel.allFormData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario para ingresar a la Up") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = asunto,
                onValueChange = { asunto = it },
                label = { Text("Asunto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.saveData(nombre, asunto, email)
                nombre = ""
                asunto = ""
                email = ""
            }) {
                Text("Guardar Dato")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Datos Guardados:", style = MaterialTheme.typography.titleMedium)

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(savedData) { data ->
                    Text(
                        text = "ID: ${data.id} | ${data.nombre} | ${data.asunto} | ${data.email}",
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}