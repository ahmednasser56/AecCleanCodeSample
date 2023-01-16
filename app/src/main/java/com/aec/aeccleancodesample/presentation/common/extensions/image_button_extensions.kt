package com.aec.aeccleancodesample.presentation.common.extensions

import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun ImageButton.changeTint(@ColorRes newColor: Int) {

    this.setColorFilter(
        ContextCompat.getColor(this.context, newColor),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}