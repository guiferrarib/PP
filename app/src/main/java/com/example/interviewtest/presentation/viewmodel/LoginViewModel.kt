package com.example.interviewtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.interviewtest.presentation.model.LoginUiAction
import com.example.interviewtest.presentation.model.LoginUiEffect
import com.example.interviewtest.presentation.model.LoginUiEvent
import com.example.interviewtest.presentation.model.LoginUiState
import com.example.interviewtest.presentation.model.UserLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

   private val _state = MutableStateFlow<LoginUiState>(LoginUiState())
    val state : StateFlow<LoginUiState> = _state

    private val _effect = MutableStateFlow<LoginUiEffect>(LoginUiEffect.InitialState)
    val effect : StateFlow<LoginUiEffect> = _effect

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnLoginClicked -> {
                if (validateCredentials(event.user)) {
                    onAction(LoginUiAction.OnLoginClicked(user = event.user))
                } else {
                    onAction(LoginUiAction.OnLoginError("Fill all the fields !"))
                }
            }

            is LoginUiEvent.OnLoginError -> {
                TODO()
            }

            is LoginUiEvent.OnLoginFailed -> {
                TODO()
            }

            is LoginUiEvent.OnLoginLoading -> {
                TODO()
            }

            is LoginUiEvent.OnLoginSuccess -> {
                TODO()
            }
        }
    }

    private fun onAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.OnLoginClicked -> {
                if (validateCredentials(action.user)) {
                    _effect.value = LoginUiEffect.NavigateToHome(action.user)
                } else {
                    _effect.value = LoginUiEffect.ShowError("Fill all the fields !")
                }
            }

            is LoginUiAction.OnLoginError -> {
                TODO()
            }

            is LoginUiAction.OnLoginFailed -> TODO()
            is LoginUiAction.OnLoginLoading -> TODO()
            is LoginUiAction.OnLoginSuccess -> TODO()
        }
    }

    //Implement validation to ensure that both username and password fields are not empty.
    private fun validateCredentials(user: UserLogin): Boolean {
        return user.userName.orEmpty().isNotEmpty() && user.userPassword.orEmpty().isNotEmpty()
    }

}