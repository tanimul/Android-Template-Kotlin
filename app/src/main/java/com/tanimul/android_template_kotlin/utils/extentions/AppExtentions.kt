package com.tanimul.android_template_kotlin.utils.extentions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tanimul.android_template_kotlin.AndroidTemplateApp
import com.tanimul.android_template_kotlin.AndroidTemplateApp.Companion.getInstance
import com.tanimul.android_template_kotlin.AndroidTemplateApp.Companion.noInternetDialog
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.databinding.LayoutNoInternetBinding
import com.tanimul.android_template_kotlin.utils.SharedPrefUtils

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Activity.launchActivityWithNewTask() {
    launchActivity<T> {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1, options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

fun getSharedPrefInstance(): SharedPrefUtils {
    return if (AndroidTemplateApp.sharedPrefUtils == null) {
        AndroidTemplateApp.sharedPrefUtils = SharedPrefUtils()
        AndroidTemplateApp.sharedPrefUtils!!
    } else {
        AndroidTemplateApp.sharedPrefUtils!!
    }
}

internal fun Drawable.tint(@ColorInt color: Int): Drawable {
    val wrapped = DrawableCompat.wrap(this)
    DrawableCompat.setTint(wrapped, color)
    return wrapped
}

fun AppCompatActivity.switchToDarkMode(isDark: Boolean) {
    if (isDark) {
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
    } else {
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
    }
}

fun ImageView.loadImageFromUrl(
    aImageUrl: String,
    aPlaceHolderImage: Int = R.drawable.placeholder_image,
    aErrorImage: Int = R.drawable.placeholder_image
) {
    try {
        if (!aImageUrl.checkIsEmpty()) {
            Glide.with(getInstance()).load(aImageUrl).placeholder(aPlaceHolderImage)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(aErrorImage).into(this)
        } else {
            loadImageFromDrawable(aPlaceHolderImage)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun ImageView.loadImageFromDrawable(@DrawableRes aPlaceHolderImage: Int) {
    Glide.with(getInstance()).load(aPlaceHolderImage).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

enum class JsonFileCode {
    NO_INTERNET, LOADER
}

fun Activity.openLottieDialog(
    jsonFileCode: JsonFileCode = JsonFileCode.NO_INTERNET, onLottieClick: () -> Unit
) {
    try {
        val jsonFile: String = when (jsonFileCode) {
            JsonFileCode.NO_INTERNET -> "lottie/no_internet.json"
            JsonFileCode.LOADER -> "lottie/no_data.json"
        }
        val dialogLayout = LayoutNoInternetBinding.inflate(this.layoutInflater)

        if (noInternetDialog == null) {
            noInternetDialog = Dialog(this, R.style.FullScreenDialog)
            noInternetDialog?.setContentView(dialogLayout.root)
            noInternetDialog?.setCanceledOnTouchOutside(false)
            noInternetDialog?.setCancelable(false)
            noInternetDialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialogLayout.parentNoInternet.onClick {
                if (!isNetworkAvailable()) {
                    snackBarError(getInstance().getString(R.string.error_no_internet))
                    return@onClick
                }
                noInternetDialog?.dismiss()
                onLottieClick()
            }
        }
        dialogLayout.lottieNoInternet.setAnimation(jsonFile)
        if (!this.isFinishing && !noInternetDialog!!.isShowing) {
            noInternetDialog?.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}