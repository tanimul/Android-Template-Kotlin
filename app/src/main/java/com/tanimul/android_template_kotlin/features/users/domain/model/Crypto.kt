package com.tanimul.android_template_kotlin.features.users.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Crypto(
    @SerializedName("coin") var coin: String? = null,
    @SerializedName("wallet") var wallet: String? = null,
    @SerializedName("network") var network: String? = null
)