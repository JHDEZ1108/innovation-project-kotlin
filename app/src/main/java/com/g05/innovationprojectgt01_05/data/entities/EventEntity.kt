package com.g05.innovationprojectgt01_05.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Represents an event stored in the local SQLite database.
 */
@Parcelize
@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])] // ✅ Se agrega índice a la foreign key
)
data class EventEntity(

    /** Auto-generated event ID */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** ID of the user who created the event (foreign key to UserEntity) */
    val userId: Int,

    /** Title of the event */
    val name: String,

    /** Detailed description */
    val description: String,

    /** Date of the event in ISO format (yyyy-MM-dd) */
    val date: String,

    /** Time of the event in HH:mm format */
    val time: String,

    /** Optional URI string of the image */
    val imageUri: String? = null,

    /** Marked as favorite by the user */
    val isFavorite: Boolean = false,

    /** Optional address or location string */
    val location: String? = null
) : Parcelable
