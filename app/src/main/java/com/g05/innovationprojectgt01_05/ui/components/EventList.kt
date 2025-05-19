package com.g05.innovationprojectgt01_05.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.g05.innovationprojectgt01_05.data.entities.EventEntity

@Composable
fun EventList(
    events: List<EventEntity>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (events.isEmpty()) {
            item {
                Text(
                    text = "No hay eventos por mostrar.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            items(events) { event ->
                EventItem(event)
            }
        }
    }
}

@Composable
fun EventItem(event: EventEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = if (event.isFavorite) {
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        } else {
            CardDefaults.cardColors()
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fecha: ${event.date}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Hora: ${event.time}", style = MaterialTheme.typography.bodySmall)

            event.location?.let {
                Text(text = "Ubicación: $it", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
