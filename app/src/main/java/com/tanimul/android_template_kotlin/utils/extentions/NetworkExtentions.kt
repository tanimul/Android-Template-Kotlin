package com.tanimul.android_template_kotlin.utils.extentions

import android.util.Log
import com.tanimul.android_template_kotlin.app.AndroidTemplateApp.Companion.getInstance
import com.tanimul.android_template_kotlin.BuildConfig
import com.tanimul.android_template_kotlin.R
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

fun getJsonMsg(errorBody: ResponseBody): String {
    try {
        val jsonObject = JSONObject(errorBody.string().getHtmlString().toString())
        Log.d("api_", jsonObject.toString())
        return if (jsonObject.has("message")) {
            (jsonObject.getString("message"))
        } else {
            getInstance().getString(R.string.error_something_went_wrong)
        }
    } catch (e: JSONException) {
        if (BuildConfig.DEBUG) {
            return e.toString()
        }
        e.printStackTrace()
    }
    return getInstance().getString(R.string.error_something_went_wrong)
}

fun getErrorMessageByHttpCode(aHttpCode: Int): String {
    return when (aHttpCode) {
        400 -> "Bad Request"
        401 -> "Unauthorized"
        404 -> "URL Not Found"
        407 -> "Proxy Authentication Required"
        408 -> "Request Timeout"
        413 -> "Payload Too Large"
        414 -> "URI Too Long"
        440 -> "Session expire"
        504, 598, 599 -> "Server timeout"
        500 -> "Internal Server Error"
        else -> "Something went wrong"
    }
}