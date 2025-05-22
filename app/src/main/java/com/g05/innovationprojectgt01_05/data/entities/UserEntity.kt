package com.g05.innovationprojectgt01_05.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user account stored locally in the SQLite database.
 */
@Entity(tableName = "users")
data class UserEntity(

    /** Local auto-generated primary key */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** Firebase UID used to associate this user with Firebase Auth */
    val firebaseUid: String,

    /** The username chosen during registration */
    val username: String,

    /** The email associated with the user */
    val email: String
)
