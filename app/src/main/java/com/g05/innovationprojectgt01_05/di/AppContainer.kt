package com.g05.innovationprojectgt01_05.di

import android.content.Context
import androidx.room.Room
import com.g05.innovationprojectgt01_05.data.AppDatabase
import com.g05.innovationprojectgt01_05.data.repository.EventRepository
import com.g05.innovationprojectgt01_05.data.repository.UserRepository

/**
 * Manual dependency injection container.
 */
class AppContainer(context: Context) {

    // Create the Room database instance
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "innovation_project_database"
    ).build()

    // Repository instances
    val eventRepository: EventRepository = EventRepository(database.eventDao())
    val userRepository: UserRepository = UserRepository(database.userDao())
}
