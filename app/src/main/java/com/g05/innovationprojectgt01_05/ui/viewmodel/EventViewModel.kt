package com.g05.innovationprojectgt01_05.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import com.g05.innovationprojectgt01_05.data.repository.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing events data and UI interaction logic.
 */
class EventViewModel(private val repository: EventRepository) : ViewModel() {

    val allEvents: StateFlow<List<EventEntity>> = repository.getAllEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
}
