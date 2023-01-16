package com.aec.aeccleancodesample.presentation.common.extensions

fun Int.returnTwoDigitNumber(): String {

    return if (this < 10)
        "0$this"
    else
        "$this"
}


