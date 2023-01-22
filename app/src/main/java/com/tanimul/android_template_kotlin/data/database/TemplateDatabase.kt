package com.tanimul.android_template_kotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanimul.android_template_kotlin.data.models.response.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class TemplateDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: TemplateDatabase? = null

        fun getDatabase(context: Context): TemplateDatabase {
            if (instance == null) {

                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context, TemplateDatabase::class.java,
                        "Template")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!

        }
    }

}