package com.tanimul.android_template_kotlin.features.users.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Bank(
    @SerializedName("cardExpire") var cardExpire: String? = null,
    @SerializedName("cardNumber") var cardNumber: String? = null,
    @SerializedName("cardType") var cardType: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("iban") var iban: String? = null
)