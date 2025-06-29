package com.tanimul.android_template_kotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tanimul.android_template_kotlin.db.dao.UserDao
import com.tanimul.android_template_kotlin.db.type_converter.UserTypeConverters
import com.tanimul.android_template_kotlin.features.users.domain.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(UserTypeConverters::class)
abstract class TemplateDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}