package com.aec.aeccleancodesample.domain.login.usecase

import com.aec.aeccleancodesample.data.model.toUser
import com.aec.aeccleancodesample.domain.login.model.User
import com.aec.aeccleancodesample.domain.login.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.aec.aeccleancodesample.presentation.common.Result

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(phoneNumber: String): Flow<Result<User>> = flow {
        authRepository.login(phoneNumber).collect { result ->
            when (result) {
                is Result.Loading -> {
                    emit(Result.Loading())
                }
                is Result.Success -> {
                    emit(Result.Success(result.data.toUser()))
                }
                is Result.Error -> {
                    emit(Result.Error(result.error))
                }
            }
        }
    }

}