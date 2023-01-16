package com.aec.aeccleancodesample.presentation.login.view

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aec.aeccleancodesample.databinding.ActivityLoginBinding
import com.aec.aeccleancodesample.presentation.common.extensions.toast
import com.aec.aeccleancodesample.presentation.common.utils.GeneralUtility
import com.aec.aeccleancodesample.presentation.login.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    //region variables
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var dialogProgress: Dialog
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        dialogProgress = GeneralUtility.getProgressDialog(this)

        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(etPhoneNumber.text.toString())
            }
        }

        observeData()
    }

    private fun observeData() {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                //region user
                launch {
                    viewModel.user.collect {
                        // here handle if login successful
                    }
                }
                //endregion

                //region showProgress
                launch {
                    viewModel.showProgress.collect {
                        dialogProgress.show()
                    }
                }
                //endregion

                //region hideProgress
                launch {
                    viewModel.hideProgress.collect {
                        dialogProgress.hide()
                    }
                }
                //endregion

                //region error
                launch {
                    viewModel.error.collect { error -> toast(error) }
                }
                //endregion
            }
        }
    }
}