package com.tanimul.android_template_kotlin.network.core

import androidx.annotation.Keep
import com.tanimul.android_template_kotlin.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@Keep
class MyServiceInterceptor@Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()

        requestBuilder
            .addHeader(AppConstants.API_ACCEPT, AppConstants.API_ACCEPT_VALUE)
            .addHeader(AppConstants.PACKAGE_ID, AppConstants.PACKAGE)

        requestBuilder.method(request.method, request.body)

        return chain.proceed(requestBuilder.build())
    }

}