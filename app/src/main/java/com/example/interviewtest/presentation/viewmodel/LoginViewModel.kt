package com.example.interviewtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewtest.presentation.model.LoginUiAction
import com.example.interviewtest.presentation.model.LoginUiEffect
import com.example.interviewtest.presentation.model.LoginUiEvent
import com.example.interviewtest.presentation.model.LoginUiState
import com.example.interviewtest.presentation.model.UserLogin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    private val _effect = MutableSharedFlow<LoginUiEffect>() // Altera para SharedFlow
    val effect: SharedFlow<LoginUiEffect> = _effect

    private val validCredentials = UserLogin("admin", "123")

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnLoginClicked -> {
                if (validateCredentials(event.user)) {
                    if (event.user == validCredentials) {
                        onAction(LoginUiAction.OnLoginSuccess(user = event.user))
                    } else {
                        onAction(LoginUiAction.OnLoginError("Invalid username or password !"))
                    }
                } else {
                    onAction(LoginUiAction.OnLoginError("Fill all the fields !"))
                }
            }
        }
    }

    private fun onAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.OnLoginError -> {
                sendEffect(LoginUiEffect.ShowError(action.message))
            }

            is LoginUiAction.OnLoginSuccess -> {
                sendEffect(LoginUiEffect.NavigateToHome(action.user))
            }
        }
    }

    //Implement validation to ensure that both username and password fields are not empty.
    private fun validateCredentials(user: UserLogin): Boolean {
        return user.userName.orEmpty().isNotEmpty() && user.userPassword.orEmpty().isNotEmpty()
    }

    private fun sendEffect(effect: LoginUiEffect) {
        viewModelScope.launch {
            _effect.emit(effect) // Garante que o evento seja emitido corretamente
        }
    }

}