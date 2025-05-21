package com.tanimul.android_template_kotlin.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.tanimul.android_template_kotlin.R

abstract class BaseActivity<E: ViewBinding> : AppCompatActivity(){

    private var _binding: E? = null

    val binding: E get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setTheme(R.style.Theme_AndroidTemplateKotlin)
        setContentView(_binding!!.root)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = Color.TRANSPARENT
        init(savedInstanceState)
    }


    protected abstract fun getViewBinding() : E
    protected abstract fun init(savedInstanceState: Bundle?)
}