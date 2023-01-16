package com.aec.aeccleancodesample.domain.login.repository

import com.aec.aeccleancodesample.data.model.UserDto
import com.aec.aeccleancodesample.presentation.common.Result
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    fun login(phoneNumber: String): Flow<Result<UserDto>>

}