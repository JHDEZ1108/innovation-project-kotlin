package com.g05.innovationprojectgt01_05.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.g05.innovationprojectgt01_05.data.AppDatabase
import com.g05.innovationprojectgt01_05.data.repository.EventRepository

/**
 * Factory to create EventViewModel with injected repository using application context.
 */
class EventViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getDatabase(context).eventDao()
        val repository = EventRepository(dao)

        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
