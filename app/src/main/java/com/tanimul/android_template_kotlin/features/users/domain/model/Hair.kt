package com.tanimul.android_template_kotlin.features.users.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Hair(
    @SerializedName("color") var color: String? = null,
    @SerializedName("type") var type: String? = null
)