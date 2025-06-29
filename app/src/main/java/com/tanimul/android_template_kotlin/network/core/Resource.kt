package com.tanimul.goza_ta.network.core

import androidx.annotation.Keep
import com.tanimul.android_template_kotlin.network.core.ErrorBody

@Keep
sealed class Resource<T>(
    var data: T? = null,
    val message: String? = null,
    val errorData: ErrorBody? = null
) {
    @Keep
    class Success<T>(data: T?, message: String? = null) : Resource<T>(data, message = message)

    @Keep
    class Error<T>(errorData: ErrorBody) :
        Resource<T>(errorData = errorData, message = errorData.message)

    @Keep
    class Loading<T>(data: T? = null) : Resource<T>(data)

    @Keep
    class NetworkError<T> : Resource<T>(message = "No Internet connection")

    @Keep
    class SessionExpired<T>(errorData: ErrorBody) :
        Resource<T>(errorData = errorData, message = errorData.message)
}