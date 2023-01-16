package com.aec.aeccleancodesample.presentation.common.extensions

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

fun MaterialCardView.changeStrokeColor(@ColorRes newColor: Int) {
    this.setStrokeColor(
        ContextCompat.getColorStateList(this.context, newColor)
    )
}