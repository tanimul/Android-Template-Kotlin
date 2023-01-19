package com.tanimul.android_template_kotlin.utils.extentions

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp.Companion.getInstance
import com.tanimul.android_template_kotlin.R
import kotlin.math.roundToInt

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.snackBar(msg: String) = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()

fun View.snackBarError(msg: String) {
    val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    val sbView = snackBar.view
    sbView.setBackgroundColor(
        ContextCompat.getColor(
            getInstance().applicationContext,
            R.color.colorPrimary
        )
    )
    snackBar.setTextColor(
        Color.WHITE
    )
    snackBar.show()
}

fun View.showIf(condition: Boolean) = if (condition) show() else hide()

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

val View.res: Resources get() = resources
val View.ctx: Context get() = context

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.changeBackgroundTint(color: Int) {
    (background as GradientDrawable).setColor(color)
    (background as GradientDrawable).setStroke(0, 0)

    background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
}

fun View.setStrokedBackground(
    backgroundColor: Int,
    strokeColor: Int = 0,
    alpha: Float = 1.0f,
    strokeWidth: Int = 3
) {
    val drawable = background as GradientDrawable
    drawable.setStroke(strokeWidth, strokeColor)
    drawable.setColor(adjustAlpha(backgroundColor, alpha))
}

fun adjustAlpha(color: Int, factor: Float): Int {
    val alpha = (Color.alpha(color) * factor).roundToInt()
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}

fun Context.getDisplayWidth(): Int = resources.displayMetrics.widthPixels

fun Context.getDisplayHeight(): Int = resources.displayMetrics.heightPixels

fun Context.convertDpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

fun RecyclerView.rvItemAnimationFallDown() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
}
