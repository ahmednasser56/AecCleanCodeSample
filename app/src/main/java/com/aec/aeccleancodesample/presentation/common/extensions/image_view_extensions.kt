package com.aec.aeccleancodesample.presentation.common.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun ImageView.changeTint(@ColorRes newColor: Int) {

    this.setColorFilter(
        ContextCompat.getColor(this.context, newColor),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}