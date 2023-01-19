package com.tanimul.android_template_kotlin.data.models.response

open class BaseResponse(
    val success: Boolean = false,
    val status: Int = 0,
    val message: String = ""
)