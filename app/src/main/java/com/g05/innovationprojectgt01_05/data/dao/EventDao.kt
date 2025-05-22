package com.g05.innovationprojectgt01_05.data.dao

import androidx.room.*
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing EventEntity data in the database.
 */
@Dao
interface EventDao {

    // Insert a new event
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    // Update an existing event
    @Update
    suspend fun updateEvent(event: EventEntity)

    // Delete an event
    @Delete
    suspend fun deleteEvent(event: EventEntity)

    // Get all events
    @Query("SELECT * FROM events ORDER BY date ASC, time ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    // Get only favorite events
    @Query("SELECT * FROM events WHERE isFavorite = 1 ORDER BY date ASC, time ASC")
    fun getFavoriteEvents(): Flow<List<EventEntity>>

    // Get event by ID
    @Query("SELECT * FROM events WHERE id = :eventId LIMIT 1")
    suspend fun getEventById(eventId: Int): EventEntity?

    // Get all events by user ID
    @Query("SELECT * FROM events WHERE userId = :userId ORDER BY date ASC, time ASC")
    fun getEventsByUserId(userId: Int): Flow<List<EventEntity>>

    // Get favorite events by user ID
    @Query("SELECT * FROM events WHERE userId = :userId AND isFavorite = 1 ORDER BY date ASC, time ASC")
    fun getFavoriteEventsByUserId(userId: Int): Flow<List<EventEntity>>
}
