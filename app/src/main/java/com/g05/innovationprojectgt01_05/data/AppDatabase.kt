package com.g05.innovationprojectgt01_05.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "innovation_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
