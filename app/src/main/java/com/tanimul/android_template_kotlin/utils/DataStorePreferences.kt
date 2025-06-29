package com.tanimul.android_template_kotlin.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tanimul.android_template_kotlin.utils.AppConstants.MY_PREFERENCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Creating data-store
private val Context.dataStore by preferencesDataStore(name = MY_PREFERENCES)

class DataStorePreferences(context: Context) {

    private val dataSource = context.dataStore

    //<editor-fold desc="Save value">
    suspend fun saveValue(key: String, value: Any?) {
        dataSource.edit { preferences ->
            when (value) {
                is Int? -> {
                    preferences[intPreferencesKey(key)] = value!!
                }

                is String? -> {
                    preferences[stringPreferencesKey(key)] = value!!
                }

                is Float? -> {
                    preferences[floatPreferencesKey(key)] = value!!
                }

                is Double? -> {
                    preferences[doublePreferencesKey(key)] = value!!
                }

                is Long? -> {
                    preferences[longPreferencesKey(key)] = value!!
                }

                is Boolean? -> {
                    preferences[booleanPreferencesKey(key)] = value!!
                }
            }

        }
    }
    //</editor-fold>

    //<editor-fold desc="Get value">
    fun getIntValue(key: String): Flow<Int?> {
        return dataSource.data.map { preferences ->
            preferences[intPreferencesKey(key)]
        }
    }

    fun getStringValue(key: String): Flow<String?> {
        return dataSource.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
    }

    fun getFloatValue(key: String): Flow<Float?> {
        return dataSource.data.map { preferences ->
            preferences[floatPreferencesKey(key)]
        }
    }

    fun getDoubleValue(key: String): Flow<Double?> {
        return dataSource.data.map { preferences ->
            preferences[doublePreferencesKey(key)]
        }
    }

    fun getLongValue(key: String): Flow<Long?> {
        return dataSource.data.map { preferences ->
            preferences[longPreferencesKey(key)]
        }
    }

    fun getBooleanValue(key: String): Flow<Boolean> {
        return dataSource.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }
    }
    //</editor-fold>

    suspend fun removeKey(key: String) {
        dataSource.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

    suspend fun clear() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }

}