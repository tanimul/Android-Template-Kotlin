package com.tanimul.android_template_kotlin.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.Keep
import com.tanimul.android_template_kotlin.features.users.data.remote.UsersRepositoryImpl
import com.tanimul.android_template_kotlin.features.users.domain.repository.UsersRepository
import com.tanimul.android_template_kotlin.network.ApiInterface
import com.tanimul.android_template_kotlin.network.core.MyServiceInterceptor
import com.tanimul.android_template_kotlin.utils.AppConstants
import com.tanimul.android_template_kotlin.utils.DataStorePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Keep
object AppModule {

    @Provides
    fun provideNetworkInterceptor(
        interceptor: MyServiceInterceptor
    ): OkHttpClient {
        val build = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES).addInterceptor(interceptor)

        //if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        build.addInterceptor(httpLoggingInterceptor)
        //}

        return build.build()
    }

    @Provides
    fun provideRetrofitInstance(
        @Named("base_url") baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Named("base_url")
    fun providesBaseUrl(): String {
        return AppConstants.API_URL
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStorePreferences {
        return DataStorePreferences(context)
    }

    @Provides
    fun provideApiInterface(
        retrofit: Retrofit
    ): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    fun provideUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository =
        usersRepositoryImpl

}