package com.tanimul.android_template_kotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanimul.android_template_kotlin.data.models.response.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class TemplateDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}