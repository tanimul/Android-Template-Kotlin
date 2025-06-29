package com.tanimul.android_template_kotlin.features.users.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Address(
    @SerializedName("address") var address: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("stateCode") var stateCode: String? = null,
    @SerializedName("postalCode") var postalCode: String? = null,
    @SerializedName("coordinates") var coordinates: Coordinates? = Coordinates(),
    @SerializedName("country") var country: String? = null
)