package com.aec.aeccleancodesample.data.model

import com.aec.aeccleancodesample.domain.login.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("fullname") val fullName: String,
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val lastName: String,
    val title: String,
    val email: String,
    val mobile: String,
    val dateOfBirth: String,
    val gender: String,
    val name: String
)

fun UserDto.toUser() = User(
    name = name,
    email = email,
    mobile = mobile,
    fullName = fullName
)