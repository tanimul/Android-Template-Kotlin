package com.tanimul.android_template_kotlin.network

import com.tanimul.android_template_kotlin.features.users.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    suspend fun fetchUsers(
        @Query("page") pageNumber: Int
    ): Response<UserResponse>

}