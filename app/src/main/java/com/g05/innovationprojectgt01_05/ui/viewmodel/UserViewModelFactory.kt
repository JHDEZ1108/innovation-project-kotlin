package com.g05.innovationprojectgt01_05.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.g05.innovationprojectgt01_05.data.AppDatabase
import com.g05.innovationprojectgt01_05.data.repository.UserRepository

/**
 * Factory to create instances of UserViewModel with required dependencies.
 */
class UserViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val userDao = AppDatabase.getDatabase(context).userDao()
        val userRepository = UserRepository(userDao)

        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                UserViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
