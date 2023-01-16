package com.aec.aeccleancodesample.presentation.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


abstract class BaseViewModel : ViewModel() {

    protected val errorFlow = MutableSharedFlow<String>()
    val error = errorFlow.asSharedFlow()

    protected val showProgressFlow = MutableSharedFlow<Boolean>()
    val showProgress = showProgressFlow.asSharedFlow()

    protected val hideProgressFlow = MutableSharedFlow<Boolean>()
    val hideProgress = hideProgressFlow.asSharedFlow()
}