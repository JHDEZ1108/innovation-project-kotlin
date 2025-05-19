package com.g05.innovationprojectgt01_05.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import com.g05.innovationprojectgt01_05.data.repository.EventRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for managing events data and UI interaction logic.
 */
class EventViewModel(private val repository: EventRepository) : ViewModel() {

    // All events
    val events: StateFlow<List<EventEntity>> = repository.getAllEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Favorite events (if still used in other UI parts)
    val favoriteEvents: StateFlow<List<EventEntity>> = repository.getFavoriteEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.insertEvent(event)
        }
    }

    fun updateEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.updateEvent(event)
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}
