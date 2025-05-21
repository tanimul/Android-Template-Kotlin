package com.tanimul.android_template_kotlin.network.core

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import com.tanimul.android_template_kotlin.common.extention.toJson
import com.tanimul.goza_ta.network.core.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


@Keep
suspend fun <T> performNetworkCallWithRetries(
    call: suspend () -> Response<T>,
    maxRetries: Int = 2
): Response<T> {
    var retryCount = 0
    while (retryCount < maxRetries) {
        try {
            return call.invoke()
        } catch (e: IOException) {
            if (retryCount == maxRetries - 1) {
                throw e
            } else {
                Timber.d("Retrying: performNetworkCallWithRetries: ")
                delay(500) // Wait for 0.5 seconds before retrying
            }
        }
        retryCount++
    }
    throw IllegalStateException("Maximum number of retries exceeded")
}


@Keep
fun <T : Any> networkCall(
    connectivityManager: ConnectivityManager,
    call: suspend () -> Response<T>
): Flow<Resource<T>> = flow {

    Timber.d("NetworkCall: Before Loading")
    emit(Resource.Loading())

    if (!connectivityManager.isInternetAvailable()) {
        Timber.d("NetworkCall: No Internet")
        emit(Resource.NetworkError())
        return@flow
    } else {
        val response = performNetworkCallWithRetries(call)
        Timber.d("NetworkCall", "$response")
        if (response.isSuccessful) {
            var message: String? = null
            try {
                val body = JSONObject(response.body()?.toJson().toString())
                if (body.has("message"))
                    message = body.getString("message")
            } catch (e: Exception) {
                Timber.d("NetworkCall", "messageParse: ${e.localizedMessage}")
            }
            Timber.d("NetworkCall", "${response.body()?.toJson()}")
            emit(Resource.Success(response.body(), message))
            return@flow
        } else {
            val jsonObject = JSONObject(response.errorBody()?.string())
            val errorBody =
                if (jsonObject.has("errors")) jsonObject.get("errors").toString() else ""
            val errorMessage =
                if (jsonObject.has("message")) jsonObject.get("message").toString() else ""

            val errorCode = response.code()
            val error = ErrorBody(errorMessage, errorCode, errorBody)

            emit(if (errorCode == 401) Resource.SessionExpired(error) else Resource.Error(error))
            return@flow
        }
    }
}.catch { e ->
    Timber.d("networkCall: " + e.localizedMessage)
    for (i in e.stackTrace) {
        Timber.d("NetworkRequest: $i")
    }
    emit(Resource.Error(ErrorBody(e.localizedMessage, null, null)))
}

fun ConnectivityManager.isInternetAvailable(): Boolean {
    val result: Boolean
    val networkCapabilities = activeNetwork ?: return false
    val actNw = getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

    return result
}

inline fun <reified T> MutableLiveData<Resource<T>>.collect(network: Flow<Resource<T>>) {
    CoroutineScope(Dispatchers.IO).launch {
        network.collect {
            postValue(it)
        }
    }
}



