package com.tanimul.android_template_kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tanimul.android_template_kotlin.utils.DataStorePrefUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) :
    AndroidViewModel(application) {


    private val uiDataStore = DataStorePrefUtils(application)


    //Get Value to Data Store
    fun getIntValue(key: String): Flow<Int?> {
        return uiDataStore.getIntValue(key)
    }

    fun getStringValue(key: String): Flow<String?> {
        return uiDataStore.getStringValue(key)
    }

    fun getFloatValue(key: String): Flow<Float?> {
        return uiDataStore.getFloatValue(key)
    }

    fun getDoubleValue(key: String): Flow<Double?> {
        return uiDataStore.getDoubleValue(key)
    }

    fun getLongValue(key: String): Flow<Long?> {
        return uiDataStore.getLongValue(key)
    }

    fun getBooleanValue(key: String): Flow<Boolean?> {
        return uiDataStore.getBooleanValue(key)
    }


    fun saveToDataStore(key: String, value: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.saveValue(key, value)
        }
    }

    fun clearDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.clear()
        }
    }

    fun removeKeyDataStore(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.removeKey(key)
        }
    }


}