package com.g05.innovationprojectgt01_05.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import com.g05.innovationprojectgt01_05.ui.theme.InnovationProjectGT0105Theme

class EventDetailsActivity : ComponentActivity() {
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (event != null) {
                        EventDetailsScreen(event = event, onBack = { finish() })
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
fun EventDetailsScreen(
    event: EventEntity,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        // Back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Detalles del Evento",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Show image if available
        event.imageUri?.let { uriString ->
            val painter = rememberAsyncImagePainter(model = Uri.parse(uriString))
            Image(
                painter = painter,
                contentDescription = "Imagen del evento",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text("Nombre:", style = MaterialTheme.typography.labelMedium)
        Text(event.name, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Descripción:", style = MaterialTheme.typography.labelMedium)
        Text(event.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Fecha:", style = MaterialTheme.typography.labelMedium)
        Text(event.date, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Hora:", style = MaterialTheme.typography.labelMedium)
        Text(event.time, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text("¿Favorito?", style = MaterialTheme.typography.labelMedium)
        Text(if (event.isFavorite) "Sí" else "No", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar ubicación si existe
        if (!event.location.isNullOrBlank()) {
            Text("Ubicación:", style = MaterialTheme.typography.labelMedium)
            Text(event.location ?: "", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val mapIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=${Uri.encode(event.location)}")
                    )
                    mapIntent.setPackage("com.google.android.apps.maps")
                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    } else {
                        Toast.makeText(context, "No se encontró Google Maps", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Ver en Google Maps")
            }
        }
    }
}
