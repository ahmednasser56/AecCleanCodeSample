package com.aec.aeccleancodesample.presentation.login.viewModel

import androidx.lifecycle.viewModelScope
import com.aec.aeccleancodesample.domain.login.model.User
import com.aec.aeccleancodesample.domain.login.usecase.LoginUseCase
import com.aec.aeccleancodesample.presentation.common.Result
import com.aec.aeccleancodesample.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    fun login(phoneNumber: String) {

        loginUseCase(phoneNumber).onEach { result ->

            when (result) {
                is Result.Loading -> {
                    showProgressFlow.emit(true)
                }
                is Result.Success -> {
                    hideProgressFlow.emit(true)
                    _user.emit(result.data)
                }
                is Result.Error -> {
                    hideProgressFlow.emit(true)
                    errorFlow.emit(result.error.message)
                }
            }

        }.launchIn(viewModelScope)
    }

}