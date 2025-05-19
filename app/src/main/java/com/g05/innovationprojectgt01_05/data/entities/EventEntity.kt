package com.g05.innovationprojectgt01_05.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Represents an event stored in the local SQLite database.
 */
@Parcelize
@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Unique ID for each event

    val name: String, // Event title
    val description: String, // Description of the event
    val date: String, // Stored in ISO format: yyyy-MM-dd
    val time: String, // Stored as HH:mm
    val imageUri: String?, // Path or URL to image (optional)
    val isFavorite: Boolean = false, // Whether the user marked this as favorite
    val location: String? = null // Optional address or place name
) : Parcelable
