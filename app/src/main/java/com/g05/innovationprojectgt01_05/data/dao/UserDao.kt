package com.g05.innovationprojectgt01_05.data.dao

import androidx.room.*
import com.g05.innovationprojectgt01_05.data.entities.UserEntity

/**
 * Data Access Object (DAO) for performing operations on the "users" table.
 */
@Dao
interface UserDao {

    /** Insert a new user into the database */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    /** Retrieve a user by their email */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    /** Retrieve a user by Firebase UID */
    @Query("SELECT * FROM users WHERE firebaseUid = :firebaseUid LIMIT 1")
    suspend fun getUserByFirebaseUid(firebaseUid: String): UserEntity?

    /** Delete all users from the table */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}

