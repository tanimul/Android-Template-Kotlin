package com.tanimul.android_template_kotlin.di

import android.content.Context
import androidx.room.Room
import com.tanimul.android_template_kotlin.data.database.TemplateDatabase
import com.tanimul.android_template_kotlin.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun getRoomDatabase(@ApplicationContext context: Context): TemplateDatabase {
        return Room.databaseBuilder(context, TemplateDatabase::class.java, "Template")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getUserDao(templateDatabase: TemplateDatabase): UserDao {
        return templateDatabase.userDao()
    }
}