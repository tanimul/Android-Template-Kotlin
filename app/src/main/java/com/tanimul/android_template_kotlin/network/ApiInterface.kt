package com.tanimul.android_template_kotlin.network

import com.tanimul.android_template_kotlin.data.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int, @Query("per_page") per_page: Int
    ): Response<List<UserResponse>>


}