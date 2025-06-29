package com.tanimul.android_template_kotlin.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanimul.android_template_kotlin.utils.DataStorePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val dataStorePreferences: DataStorePreferences) :
    ViewModel() {

    //<editor-fold desc="Save value">
    fun saveToDataStore(key: String, value: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePreferences.saveValue(key, value)
        }
    }
    //</editor-fold>

    //<editor-fold desc="Get value">
    fun getIntValue(key: String): Flow<Int?> {
        return dataStorePreferences.getIntValue(key)
    }

    fun getStringValue(key: String): Flow<String?> {
        return dataStorePreferences.getStringValue(key)
    }

    fun getFloatValue(key: String): Flow<Float?> {
        return dataStorePreferences.getFloatValue(key)
    }

    fun getDoubleValue(key: String): Flow<Double?> {
        return dataStorePreferences.getDoubleValue(key)
    }

    fun getLongValue(key: String): Flow<Long?> {
        return dataStorePreferences.getLongValue(key)
    }

    fun getBooleanValue(key: String): Flow<Boolean?> {
        return dataStorePreferences.getBooleanValue(key)
    }

    //</editor-fold>

    fun removeKeyDataStore(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePreferences.removeKey(key)
        }
    }

    fun clearDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePreferences.clear()
        }
    }

}