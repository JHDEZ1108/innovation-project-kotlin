package com.g05.innovationprojectgt01_05.ui.screens

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.g05.innovationprojectgt01_05.auth.LoginActivity
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import com.g05.innovationprojectgt01_05.ui.theme.InnovationProjectGT0105Theme
import com.g05.innovationprojectgt01_05.ui.viewmodel.EventViewModel
import com.g05.innovationprojectgt01_05.ui.viewmodel.EventViewModelFactory

class HomeActivity : ComponentActivity() {
    private val viewModel: EventViewModel by viewModels {
        EventViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editEventLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedEvent: EventEntity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("updatedEvent", EventEntity::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    result.data?.getParcelableExtra("updatedEvent")
                }

                updatedEvent?.let {
                    viewModel.updateEvent(it)
                }
            }
        }

        setContent {
            InnovationProjectGT0105Theme {
                var showAddScreen by remember { mutableStateOf(false) }

                if (showAddScreen) {
                    AddEventScreen(
                        onSave = { event ->
                            viewModel.insertEvent(event)
                            showAddScreen = false
                        },
                        onCancel = {
                            showAddScreen = false
                        }
                    )
                } else {
                    val events by viewModel.events.collectAsState()

                    HomeScreen(
                        events = events,
                        onLogout = {
                            viewModel.logout()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        },
                        onAddEvent = {
                            showAddScreen = true
                        },
                        onEditEvent = { event ->
                            val intent = Intent(this, EditEventActivity::class.java)
                            intent.putExtra("event", event)
                            editEventLauncher.launch(intent)
                        },
                        onDeleteEvent = { event ->
                            viewModel.deleteEvent(event)
                        },
                        onViewEvent = {
                            // TODO: abrir pantalla de detalles
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    events: List<EventEntity>,
    onLogout: () -> Unit,
    onAddEvent: () -> Unit,
    onEditEvent: (EventEntity) -> Unit,
    onDeleteEvent: (EventEntity) -> Unit,
    onViewEvent: (EventEntity) -> Unit
) {
    val favorites = events.filter { it.isFavorite }
    val others = events.filterNot { it.isFavorite }

    Scaffold(
        topBar = {
            val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topPadding, start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Innovation Events",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                IconButton(onClick = onLogout) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Cerrar sesión"
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEvent) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar evento")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            if (favorites.isNotEmpty()) {
                Text("Favoritos", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                EventList(events = favorites, onEditEvent = onEditEvent, onDeleteEvent = onDeleteEvent, onViewEvent = onViewEvent)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text("Todos los eventos", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            EventList(events = others, onEditEvent = onEditEvent, onDeleteEvent = onDeleteEvent, onViewEvent = onViewEvent)
        }
    }
}

@Composable
fun EventList(
    events: List<EventEntity>,
    onEditEvent: (EventEntity) -> Unit,
    onDeleteEvent: (EventEntity) -> Unit,
    onViewEvent: (EventEntity) -> Unit
) {
    LazyColumn {
        items(events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onViewEvent(event) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = event.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = event.description, style = MaterialTheme.typography.bodySmall)
                            Text(text = "${event.date} - ${event.time}", style = MaterialTheme.typography.bodySmall)
                        }
                        Row(horizontalArrangement = Arrangement.End) {
                            IconButton(onClick = { onEditEvent(event) }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = { onDeleteEvent(event) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
