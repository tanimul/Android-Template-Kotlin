package com.tanimul.android_template_kotlin

import android.app.Application
import android.app.Dialog
import com.tanimul.android_template_kotlin.utils.Constants
import com.tanimul.android_template_kotlin.utils.SharedPrefUtils
import com.tanimul.android_template_kotlin.utils.extentions.getSharedPrefInstance

class AndroidTemplateApp : Application() {


    override fun onCreate() {
        instance = this
        super.onCreate()
        getSharedPrefInstance().apply {
            appTheme = getIntValue(Constants.SharedPref.KEY_THEME, Constants.THEME.LIGHT)

        }
    }


    companion object {
        private lateinit var instance: AndroidTemplateApp
        var sharedPrefUtils: SharedPrefUtils? = null
        var noInternetDialog: Dialog? = null
        var appTheme: Int = 0
        fun getInstance(): AndroidTemplateApp {
            return instance
        }

        fun changeAppTheme(isDark: Boolean) {
            getSharedPrefInstance().apply {
                when {
                    isDark -> setValue(Constants.SharedPref.KEY_THEME, Constants.THEME.DARK)
                    else -> setValue(Constants.SharedPref.KEY_THEME, Constants.THEME.LIGHT)
                }
                appTheme = getIntValue(Constants.SharedPref.KEY_THEME, Constants.THEME.LIGHT)
            }

        }
    }

}