package com.aec.aeccleancodesample.presentation.common.extensions

import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val pattern = Pattern.compile(
        "[a-zA-Z0-9+\\.\\_\\%\\-]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    return pattern.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    //Minimum eight characters, at least one letter and one number
    val pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    return pattern.matcher(this).matches()
}

fun String.replaceLastNWithAsterisk(n: Int): String {

    val length: Int = this.length

    return if (length < n) "Error"
    else this.substring(0, length - n) + "****"
}