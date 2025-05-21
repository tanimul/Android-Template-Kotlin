package com.tanimul.android_template_kotlin.di

import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.Keep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
@Module
@InstallIn(ActivityRetainedComponent::class)
@Keep
object ActivityModule {

    @Provides
    fun provideNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

}