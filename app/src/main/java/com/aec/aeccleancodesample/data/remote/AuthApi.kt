package com.aec.aeccleancodesample.data.remote


import com.aec.aeccleancodesample.data.model.UserDto
import retrofit2.http.GET

interface AuthApi {

    @GET("login")
    suspend fun login(): UserDto

}