package com.g05.innovationprojectgt01_05.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.g05.innovationprojectgt01_05.data.entities.EventEntity

@Composable
fun AddEventScreen(
    onSave: (EventEntity) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        color = colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding + 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Agregar Evento",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del evento") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Fecha (ej: 2025-05-19)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Hora (ej: 14:30)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Checkbox(checked = isFavorite, onCheckedChange = { isFavorite = it })
                Text(text = "Marcar como favorito")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onCancel) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (name.isNotBlank() && date.isNotBlank() && time.isNotBlank()) {
                            val newEvent = EventEntity(
                                name = name,
                                description = description,
                                date = date,
                                time = time,
                                isFavorite = isFavorite,
                                imageUri = null
                            )
                            onSave(newEvent)
                        } else {
                            Toast.makeText(context, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
