package com.tanimul.android_template_kotlin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<User>)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getUsers(): Flow<List<User>>

    @Query("DELETE FROM users")
    suspend fun clearAll()

}