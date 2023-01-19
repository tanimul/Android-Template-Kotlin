package com.tanimul.android_template_kotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp.Companion.getInstance
import com.tanimul.android_template_kotlin.utils.Constants.SharedPref.myPreferences

class SharedPrefUtils {
    private val pref: SharedPreferences = getInstance().applicationContext.getSharedPreferences(
        myPreferences, Context.MODE_PRIVATE
    )
    private var editor: SharedPreferences.Editor = pref.edit()

    init {
        editor.apply()
    }

    fun setValue(key: String, value: Any?) {
        when (value) {
            is Int? -> {
                editor.putInt(key, value!!).apply()
            }
            is String? -> {
                editor.putString(key, value!!).apply()
            }
            is Double? -> {
                editor.putString(key, value.toString()).apply()
            }
            is Long? -> {
                editor.putLong(key, value!!).apply()
            }
            is Boolean? -> {
                editor.putBoolean(key, value!!).apply()
            }
        }
    }

    fun getStringValue(key: String, defaultValue: String = ""): String {
        return pref.getString(key, defaultValue)!!
    }

    fun getIntValue(key: String, defaultValue: Int): Int {
        return pref.getInt(key, defaultValue)
    }

    fun getLongValue(key: String, defaultValue: Long): Long {
        return pref.getLong(key, defaultValue)
    }

    fun getBooleanValue(key: String, defaultValue: Boolean = false): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

    fun removeKey(key: String) {
        editor.remove(key).apply()
    }

    fun clear() {
        editor.clear().apply()
    }
}