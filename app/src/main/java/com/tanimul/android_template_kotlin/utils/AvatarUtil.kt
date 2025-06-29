package com.tanimul.android_template_kotlin.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import com.tanimul.avatarx.GenerateAvatar
import com.tanimul.avatarx.enum.ColorShade

object AvatarUtil {

    fun generateAvatar(
        context: Context,
        name: String = "",
        avatarSize: Int = 128,
        textSize: Int = 30,
        useRandomColor: Boolean = false,
        colorShade: ColorShade = ColorShade.MEDIUM,
        isSquare: Boolean = true,
        isCircle: Boolean = true
    ): BitmapDrawable {

        return GenerateAvatar.AvatarBuilder(context)
            .setLabel(name)
            .setAvatarSize(avatarSize)
            .setTextSize(textSize)
            .useRandomColor(useRandomColor)
            .setColorShade(colorShade)
            .apply {
                if (isSquare) toSquare()
                if (isCircle) toCircle()
            }
            .build()
    }
}
