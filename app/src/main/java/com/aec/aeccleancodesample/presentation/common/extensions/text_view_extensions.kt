package com.aec.aeccleancodesample.presentation.common.extensions

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.changeColor(@ColorRes newColor: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, newColor))
}