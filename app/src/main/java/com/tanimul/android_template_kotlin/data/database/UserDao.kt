package com.tanimul.android_template_kotlin.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanimul.android_template_kotlin.data.models.response.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userModel: UserModel)

    @Query("SELECT * FROM User ORDER BY id ASC")
    fun showUserList(): LiveData<List<UserModel>>


}