package com.tanimul.android_template_kotlin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserModel>)

    @Query("SELECT * FROM User ORDER BY id ASC")
    fun showUserList(): Flow<List<UserModel>>

}