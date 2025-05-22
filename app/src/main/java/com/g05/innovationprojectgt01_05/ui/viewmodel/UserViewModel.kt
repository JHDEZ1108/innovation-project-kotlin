package com.g05.innovationprojectgt01_05.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.g05.innovationprojectgt01_05.data.entities.UserEntity
import com.g05.innovationprojectgt01_05.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing user-related logic and exposing user data to the UI.
 */
class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    /**
     * Inserts a user into the database.
     */
    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    /**
     * Loads a user from the database using their email.
     */
    fun loadUserByEmail(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            _currentUser.value = user
        }
    }

    /**
     * Loads a user from the database using their Firebase UID.
     */
    fun loadUserByFirebaseUid(firebaseUid: String) {
        viewModelScope.launch {
            val user = repository.getUserByFirebaseUid(firebaseUid)
            _currentUser.value = user
        }
    }

    /**
     * Inserts a new user only if it doesn't already exist in the database.
     */
    fun insertUserIfNotExists(uid: String, username: String, email: String) {
        viewModelScope.launch {
            val existingUser = repository.getUserByFirebaseUid(uid)
            if (existingUser == null) {
                val newUser = UserEntity(
                    firebaseUid = uid,
                    username = username,
                    email = email
                )
                repository.insertUser(newUser)
            }
        }
    }

    /**
     * Clears the currently loaded user.
     */
    fun clearUser() {
        _currentUser.value = null
    }
}
