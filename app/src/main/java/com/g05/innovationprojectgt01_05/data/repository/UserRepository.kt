package com.g05.innovationprojectgt01_05.data.repository

import com.g05.innovationprojectgt01_05.data.dao.UserDao
import com.g05.innovationprojectgt01_05.data.entities.UserEntity

/**
 * Repository responsible for handling user-related data operations.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Insert a new user into the database.
     * @return the row ID of the newly inserted user.
     */
    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    /**
     * Insert the user only if it does not exist.
     */
    suspend fun insertUserIfNotExists(email: String, uid: String, username: String) {
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser == null) {
            val newUser = UserEntity(
                firebaseUid = uid,
                username = username,
                email = email
            )
            userDao.insertUser(newUser)
        }
    }

    /**
     * Get a user by their email.
     * @return the UserEntity if found, or null.
     */
    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    /**
     * Get a user by their Firebase UID.
     * @return the UserEntity if found, or null.
     */
    suspend fun getUserByFirebaseUid(firebaseUid: String): UserEntity? {
        return userDao.getUserByFirebaseUid(firebaseUid)
    }

    /**
     * Delete all users from the database (for testing or reset purposes).
     */
    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}
