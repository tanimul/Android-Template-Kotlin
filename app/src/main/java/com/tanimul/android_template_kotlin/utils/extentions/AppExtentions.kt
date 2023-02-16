package com.tanimul.android_template_kotlin.utils.extentions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp.Companion.getInstance
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp.Companion.noDialog
import com.tanimul.android_template_kotlin.databinding.LayoutLottieBinding

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
    NO_INTERNET, LOADER, UNDER_DEVELOPMENT
}

fun Activity.openLottieDialog(
    jsonFileCode: JsonFileCode, onLottieClick: () -> Unit
) {
    Log.d("openLottieDialog", "openLottieDialog: $jsonFileCode")
    try {
        val jsonFile: String = when (jsonFileCode) {
            JsonFileCode.NO_INTERNET -> "lottie/no_internet.json"
            JsonFileCode.LOADER -> "lottie/loader.json"
            JsonFileCode.UNDER_DEVELOPMENT -> "lottie/under_development.json"
        }
        val dialogLayout = LayoutLottieBinding.inflate(this.layoutInflater)

        if (noDialog == null) {
            noDialog = Dialog(this, R.style.FullScreenDialog)
            noDialog?.setContentView(dialogLayout.root)
            noDialog?.setCanceledOnTouchOutside(false)
            noDialog?.setCancelable(false)
            noDialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )

        dialogLayout.btnTapRetry.onClick {
            if (!isNetworkAvailable()) {
                snackBarError(getInstance().getString(R.string.error_no_internet))
                return@onClick
            }
            noDialog?.dismiss()
            onLottieClick()
        }

            dialogLayout.parentLottie.onClick {
                if (!isNetworkAvailable()) {
                    snackBarError(getInstance().getString(R.string.error_no_internet))
                    return@onClick
                }
                noDialog?.dismiss()
                onLottieClick()
            }
        }
        dialogLayout.lottie.setAnimation(jsonFile)
        if (!this.isFinishing && !noDialog!!.isShowing) {
            noDialog?.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}