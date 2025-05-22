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
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _userId = MutableStateFlow<Int?>(null)

    // All events for the current user
    val events: StateFlow<List<EventEntity>> = _userId
        .filterNotNull()
        .flatMapLatest { repository.getEventsByUserId(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Favorite events for the current user
    val favoriteEvents: StateFlow<List<EventEntity>> = _userId
        .filterNotNull()
        .flatMapLatest { repository.getFavoriteEventsByUserId(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Sets the current user ID to filter events accordingly.
     */
    fun setUserId(userId: Int) {
        _userId.value = userId
    }

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
