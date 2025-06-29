package com.tanimul.android_template_kotlin.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.tanimul.android_template_kotlin.R

class LoadingDialog(context: Context?, cancelable: Boolean = false, text: String?) {
    private var dialog: Dialog? = null

    init {
        dialog = Dialog(context!!, android.R.style.Theme_Material_Dialog).apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.layout_progress_dialog)
            setCancelable(cancelable)
        }
    }

    fun show() {
        dialog!!.show()
    }

    val isShowing: Boolean
        get() = dialog!!.isShowing

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
    }

    fun clearDialog() {
        if (isShowing) dismiss()
        dialog = null
    }

    fun dismiss(activity: Activity) {
        if (activity.isDestroyed) return
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
    }

}