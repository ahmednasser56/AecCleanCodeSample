package com.aec.aeccleancodesample.presentation.common.extensions

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DimenRes
import com.aec.aeccleancodesample.R
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.show()
    return snack
}

fun View.snackBar(text: Int, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.show()
    return snack
}

fun View.setSize(@DimenRes width: Int, @DimenRes height: Int) {

    this.layoutParams = LinearLayout.LayoutParams(
        this.context.resources.getDimension(width).toInt(),
        this.context.resources.getDimension(height).toInt()
    ).apply {

        marginStart = context.resources.getDimension(R.dimen.margin_normal).toInt()
        marginEnd = context.resources.getDimension(R.dimen.margin_normal).toInt()
        //marginEnd = 8
    }

}

fun View.setMargins(
    @DimenRes start: Int? = null,
    @DimenRes end: Int? = null,
    @DimenRes top: Int? = null,
    @DimenRes bottom: Int? = null
) {

    this.layoutParams = LinearLayout.LayoutParams(
        this.layoutParams.width,
        this.layoutParams.height
    ).apply {
        start?.run { marginStart = context.resources.getDimension(start).toInt() }
        end?.run { marginEnd = context.resources.getDimension(end).toInt() }
        top?.run { topMargin = context.resources.getDimension(top).toInt() }
        bottom?.run { bottomMargin = context.resources.getDimension(bottom).toInt() }
    }

}