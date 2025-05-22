package com.tanimul.android_template_kotlin.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.utils.AvatarUtil.generateAvatar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("formattedDateTime")
fun TextView.setFormattedDateTime(date: Date?) {
    date?.let {
        val formatted = SimpleDateFormat("dd MMM yyyy, hh.mm a", Locale.getDefault()).format(it)
        text = formatted
    }
}

@BindingAdapter("imageUrl", "userName", requireAll = false)
fun ImageView.setUserImage(imageUrl: String?, userName: String = "User") {
    Glide.with(context)
        .load(imageUrl ?: generateAvatar(context, userName))
        .error(R.drawable.ic_user)
        .into(this)
}

@BindingAdapter("gender", "age", "weight", requireAll = false)
fun TextView.setUserInfo(
    gender: String?,
    age: Int?,
    weight: Double?
) {
    text = listOfNotNull(
        gender,
        age,
        weight?.let { "$it kg" }
    ).joinToString(" | ")
}

@BindingAdapter("goneUnless")
fun View.goneUnless(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

