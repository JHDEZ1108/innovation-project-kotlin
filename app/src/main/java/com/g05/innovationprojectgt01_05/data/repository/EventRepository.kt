package com.g05.innovationprojectgt01_05.data.repository

import com.g05.innovationprojectgt01_05.data.dao.EventDao
import com.g05.innovationprojectgt01_05.data.entities.EventEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing event-related data operations.
 */
class EventRepository(private val eventDao: EventDao) {

    suspend fun insertEvent(event: EventEntity) {
        eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: EventEntity) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEvent(event: EventEntity) {
        eventDao.deleteEvent(event)
    }

    fun getAllEvents(): Flow<List<EventEntity>> {
        return eventDao.getAllEvents()
    }

    fun getFavoriteEvents(): Flow<List<EventEntity>> {
        return eventDao.getFavoriteEvents()
    }
}
