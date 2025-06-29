package com.tanimul.android_template_kotlin.common.extention

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tanimul.android_template_kotlin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@BindingAdapter("loadImageFromDrawable")
fun ImageView.loadImageFromDrawable(@DrawableRes aPlaceHolderImage: Int) {
    Glide.with(this.context).load(aPlaceHolderImage).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("imageUrl")
fun ImageView.loadImageUrl(imageUrl: String?) {
    try {
        if (imageUrl != null) {
            Glide.with(this.context).load(imageUrl).placeholder(R.drawable.logo_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).error(R.drawable.logo_placeholder)
                .into(this)
        } else {
            loadImageFromDrawable(R.drawable.logo_placeholder)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

inline fun Fragment.launchWhenCreatedLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}


@BindingAdapter("app:currencyAmount", "app:currencyCode", requireAll = true)
fun setCurrencyAmount(textView: TextView, amount: Int?, currencyCode: String?) {
    if (amount != null) {
        val locale = when (currencyCode) {
            "USD" -> Locale.US
            "EUR" -> Locale("de", "DE")  // Euro (Germany)
            "JPY" -> Locale.JAPAN          // Japanese Yen
            "GBP" -> Locale.UK             // British Pound
            "AUD" -> Locale("en", "AU")    // Australian Dollar
            "CAD" -> Locale.CANADA         // Canadian Dollar
            "INR" -> Locale("en", "IN")    // Indian Rupee
            "BDT" -> Locale("en", "BD")    // Bangladesh Taka
            // Add more currencies as needed
            else -> Locale.US  // Default to USD
        }

        val formattedAmount =
            NumberFormat.getCurrencyInstance(locale).apply { maximumFractionDigits = 0 }
                .format(amount)
        val output = formattedAmount.replace(Regex("^(\\p{Sc})(\\d)"), "$1 $2")
        textView.text = output
    } else {
        textView.text = ""
    }
}

