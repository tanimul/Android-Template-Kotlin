package com.tanimul.android_template_kotlin.utils.extentions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.Spanned
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp.Companion.getInstance
import com.tanimul.android_template_kotlin.R
import org.json.JSONObject
import java.io.ByteArrayOutputStream

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }


@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun isNetworkAvailable(): Boolean {
    val info = getInstance().getConnectivityManager().getNetworkCapabilities(getInstance().getConnectivityManager().activeNetwork)
    info.also {
        if (it != null) {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            }
        }
    }

    return false
}

fun String.checkIsEmpty(): Boolean =
    isNullOrEmpty() || "" == this || this.equals("null", ignoreCase = true)

fun String.getHtmlString(): Spanned = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

fun getStringToJSON(name: String, value: String): JSONObject {
    return JSONObject("{$name: $value}")
}

fun Activity.snackBar(msg: String, length: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(findViewById(android.R.id.content), msg, length).setTextColor(Color.WHITE).show()

fun Fragment.snackBar(msg: String) = activity!!.snackBar(msg)

fun Activity.snackBarError(msg: String) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
    val sbView = snackBar.view
    sbView.setBackgroundColor(getInstance().resources.getColor(R.color.colorPrimary))
    snackBar.setTextColor(Color.WHITE)
    snackBar.show()
}

fun Snackbar.setTextColor(color: Int): Snackbar {
    val tv = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    tv.setTextColor(color)
    return this
}

fun Activity.showPermissionAlert(view: View) {
    val snackBar = Snackbar.make(
        view, getString(R.string.error_permission_required), Snackbar.LENGTH_INDEFINITE
    )
    val sbView = snackBar.view
    snackBar.setAction("Enable") {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
        snackBar.dismiss()
    }
    sbView.setBackgroundColor(getInstance().resources.getColor(R.color.colorPrimary));snackBar.setTextColor(
        Color.WHITE
    );snackBar.show()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) =
    supportFragmentManager.inTransaction { replace(frameId, fragment) }

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) =
    supportFragmentManager.inTransaction { add(frameId, fragment) }

fun AppCompatActivity.removeFragment(fragment: Fragment) =
    supportFragmentManager.inTransaction { remove(fragment) }

fun AppCompatActivity.showFragment(fragment: Fragment) = supportFragmentManager.inTransaction {
    setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    show(fragment)
}

fun AppCompatActivity.hideFragment(fragment: Fragment) = supportFragmentManager.inTransaction {
    hide(fragment)
}

fun Context.getUriFromBitmap(inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        this.contentResolver, inImage, System.currentTimeMillis().toString(), null
    )
    return Uri.parse(path)
}