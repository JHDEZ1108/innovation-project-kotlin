package com.g05.innovationprojectgt01_05.ui.screens

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import com.g05.innovationprojectgt01_05.ui.theme.InnovationProjectGT0105Theme

class EditEventActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val event: EventEntity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("event", EventEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("event")
        }

        setContent {
            InnovationProjectGT0105Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (event != null) {
                        EditEventScreen(
                            originalEvent = event,
                            onSave = {
                                val resultIntent = intent
                                resultIntent.putExtra("updatedEvent", it)
                                setResult(RESULT_OK, resultIntent)
                                finish()
                            },
                            onCancel = {
                                finish()
                            }
                        )
                    } else {
                        Toast.makeText(this, "Evento no válido", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun EditEventScreen(
    originalEvent: EventEntity,
    onSave: (EventEntity) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(originalEvent.name) }
    var description by remember { mutableStateOf(originalEvent.description) }
    var date by remember { mutableStateOf(originalEvent.date) }
    var time by remember { mutableStateOf(originalEvent.time) }
    var isFavorite by remember { mutableStateOf(originalEvent.isFavorite) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text("Editar Evento", style = MaterialTheme.typography.titleLarge)
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
                        val updated = originalEvent.copy(
                            name = name,
                            description = description,
                            date = date,
                            time = time,
                            isFavorite = isFavorite
                        )
                        onSave(updated)
                    } else {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Guardar")
            }
        }
    }
}

