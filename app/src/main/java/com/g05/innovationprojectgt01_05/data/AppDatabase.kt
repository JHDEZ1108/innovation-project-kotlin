package com.g05.innovationprojectgt01_05.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.g05.innovationprojectgt01_05.data.dao.EventDao
import com.g05.innovationprojectgt01_05.data.entities.EventEntity

/**
 * Main Room database for the application.
 * Includes all entities and DAOs.
 */
@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAO definitions
    abstract fun eventDao(): EventDao
}
