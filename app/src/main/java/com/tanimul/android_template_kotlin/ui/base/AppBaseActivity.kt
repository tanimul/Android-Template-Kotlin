package com.tanimul.android_template_kotlin.ui.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tanimul.android_template_kotlin.R

open class AppBaseActivity : AppCompatActivity() {

    private var progressDialog: Dialog? = null
    private val TAG: String = "AppBaseActivity"

    fun setToolbarWithoutBackButton(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
    }

    fun setToolbar(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationIcon(R.drawable.ic_back)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(0))
            progressDialog?.setContentView(R.layout.layout_progress_dialog)
        }

    }
    

    fun showProgress(show: Boolean) {
        when {
            show -> {
                if (!isFinishing && !progressDialog!!.isShowing) {
                    progressDialog?.setCanceledOnTouchOutside(false)
                    progressDialog?.show()
                }
            }
            else -> try {
                if (progressDialog?.isShowing!! && !isFinishing) {
                    progressDialog?.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        Log.d(TAG, "onStart Called")
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

}
