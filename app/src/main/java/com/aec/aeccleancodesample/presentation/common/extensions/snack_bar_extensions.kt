package com.aec.aeccleancodesample.presentation.common.extensions

import android.view.View
import androidx.core.content.ContextCompat
import com.aec.aeccleancodesample.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.action(
    action: String,
    color: Int = R.color.purple_500,
    listener: (View) -> Unit
) {
    setAction(action, listener)
    setActionTextColor(ContextCompat.getColor(context, color))
}

fun Snackbar.action(
    action: Int,
    color: Int = R.color.purple_500,
    listener: (View) -> Unit
) {
    setAction(action, listener)
    setActionTextColor(ContextCompat.getColor(context, color))
}