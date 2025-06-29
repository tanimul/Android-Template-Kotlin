package com.tanimul.android_template_kotlin.features.users.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Coordinates(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lng") var lng: Double? = null
)