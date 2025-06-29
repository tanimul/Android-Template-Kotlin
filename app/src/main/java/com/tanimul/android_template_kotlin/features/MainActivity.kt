package com.tanimul.android_template_kotlin.features

import android.os.Bundle
import com.tanimul.android_template_kotlin.base.BaseActivity
import com.tanimul.android_template_kotlin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
    }

}