package com.tanimul.android_template_kotlin.features.users.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    @SerializedName("users") var users: ArrayList<User> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("skip") var skip: Int? = null,
    @SerializedName("limit") var limit: Int? = null
)