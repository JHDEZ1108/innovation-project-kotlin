package com.g05.innovationprojectgt01_05.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
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
        setContent {
            InnovationProjectGT0105Theme {
                val events by viewModel.events.collectAsState()

                HomeScreen(
                    events = events,
                    onLogout = {
                        viewModel.logout()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    },
                    onAddEvent = {
                        // TODO: Navegar a AddEventActivity o Composable
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    events: List<EventEntity>,
    onLogout: () -> Unit,
    onAddEvent: () -> Unit
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
                EventList(events = favorites)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text("Todos los eventos", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            EventList(events = others)
        }
    }
}

@Composable
fun EventList(events: List<EventEntity>) {
    LazyColumn {
        items(events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = event.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = event.description, style = MaterialTheme.typography.bodySmall)
                    Text(text = "${event.date} - ${event.time}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

