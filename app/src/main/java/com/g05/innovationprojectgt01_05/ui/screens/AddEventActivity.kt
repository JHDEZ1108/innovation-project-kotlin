package com.g05.innovationprojectgt01_05.ui.screens

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.File
import java.io.FileOutputStream

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
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var location by remember { mutableStateOf<String?>(null) }

    fun copyImageToInternalStorage(context: Context, uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "event_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val copiedUri = copyImageToInternalStorage(context, it)
            if (copiedUri != null) {
                imageUri = copiedUri
            } else {
                Toast.makeText(context, "Error al guardar imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val place = Autocomplete.getPlaceFromIntent(data!!)
            val addressComponents = place.addressComponents
            val address = addressComponents?.asList()?.joinToString(" ") { it.name }
            location = address
        } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) {
            Toast.makeText(context, "Error al seleccionar ubicación", Toast.LENGTH_SHORT).show()
        }
    }

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

            Spacer(modifier = Modifier.height(16.dp))

            Text("Imagen representativa (opcional)", style = MaterialTheme.typography.labelMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Toca para seleccionar una imagen", color = colorScheme.onSurface.copy(alpha = 0.6f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Ubicación del evento (opcional)", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = location ?: "",
                onValueChange = {},
                label = { Text("Seleccionar ubicación") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val fields = listOf(Place.Field.ID, Place.Field.ADDRESS_COMPONENTS)
                        val intent = Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.OVERLAY, fields
                        ).build(context)
                        locationLauncher.launch(intent)
                    },
                readOnly = true,
                enabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
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
                                imageUri = imageUri?.toString(),
                                location = location
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

