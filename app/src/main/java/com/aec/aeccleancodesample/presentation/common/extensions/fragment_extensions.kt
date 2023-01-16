package com.aec.aeccleancodesample.presentation.common.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, text, duration).show()
}
fun Fragment.toast(text: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, text, duration).show()
}
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
