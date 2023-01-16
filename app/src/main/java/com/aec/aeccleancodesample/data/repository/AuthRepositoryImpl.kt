package com.aec.aeccleancodesample.data.repository

import com.aec.aeccleancodesample.data.model.UserDto
import com.aec.aeccleancodesample.data.remote.AuthApi
import com.aec.aeccleancodesample.domain.login.repository.AuthRepository
import com.aec.aeccleancodesample.presentation.common.Result
import com.aec.aeccleancodesample.presentation.common.utils.NetworkUtility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkUtility: NetworkUtility,
    private val authApi: AuthApi
) : AuthRepository {

    override fun login(phoneNumber: String): Flow<Result<UserDto>> = flow {
        networkUtility.safeApiCall { authApi.login() }
            .collect { result -> emit(result) }
    }

}