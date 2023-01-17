package com.tanimul.android_template_kotlin.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.tanimul.android_template_kotlin.R

class CustomProgressDialog(private val context: Context) {
    private var alertDialog: AlertDialog? = null


    fun showProgressbar(message: String?) {
        val builder = AlertDialog.Builder(context)
        val dialogView = View.inflate(context, R.layout.layout_progress_dialog, null)
        builder.setCancelable(false)
        builder.setView(dialogView)
        val progressText = dialogView.findViewById<TextView>(R.id.progress_bar_text)
        if (message != null) {
            progressText.text = message
        }
        alertDialog = builder.create()
        alertDialog!!.show()
    }

    fun dismiss() {
        alertDialog!!.dismiss()
    }
}