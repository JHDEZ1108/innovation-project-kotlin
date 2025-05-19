package com.g05.innovationprojectgt01_05.data.repository

import com.g05.innovationprojectgt01_05.data.dao.EventDao
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing event-related operations.
 * Provides a clean API for accessing data from the EventDao.
 */
class EventRepository(private val eventDao: EventDao) {

    /**
     * Inserts a new event into the database.
     */
    suspend fun insertEvent(event: EventEntity) {
        eventDao.insertEvent(event)
    }

    /**
     * Updates an existing event in the database.
     */
    suspend fun updateEvent(event: EventEntity) {
        eventDao.updateEvent(event)
    }

    /**
     * Deletes an event from the database.
     */
    suspend fun deleteEvent(event: EventEntity) {
        eventDao.deleteEvent(event)
    }

    /**
     * Returns a flow of all events in the database.
     */
    fun getAllEvents(): Flow<List<EventEntity>> {
        return eventDao.getAllEvents()
    }

    /**
     * Returns a flow of favorite events only.
     */
    fun getFavoriteEvents(): Flow<List<EventEntity>> {
        return eventDao.getFavoriteEvents()
    }
}
