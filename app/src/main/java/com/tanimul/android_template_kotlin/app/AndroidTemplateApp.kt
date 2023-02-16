package com.tanimul.android_template_kotlin.app

import android.app.Application
import android.app.Dialog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidTemplateApp : Application() {


    override fun onCreate() {
        instance = this
        super.onCreate()
    }


    companion object {
        private lateinit var instance: AndroidTemplateApp
        var noDialog: Dialog? = null
        fun getInstance(): AndroidTemplateApp {
            return instance
        }
    }

}