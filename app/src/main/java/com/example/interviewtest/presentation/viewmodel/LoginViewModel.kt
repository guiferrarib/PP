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


/**
 * ViewModel responsible for handling user login logic.
 * Uses StateFlow to manage UI state and SharedFlow to emit UI effects.
 */
class LoginViewModel : ViewModel() {

    // Holds the current UI state of the login screen
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    // Emits one-time UI events such as navigation or showing error messages
    private val _effect = MutableSharedFlow<LoginUiEffect>() // Altera para SharedFlow
    val effect: SharedFlow<LoginUiEffect> = _effect

    // Hardcoded valid credentials for login validation
    private val validCredentials = UserLogin("admin", "123")

    /**
     * Handles incoming UI events and processes them accordingly.
     */
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

    /**
     * Processes actions and triggers corresponding UI effects.
     */
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

    /**
     * Validates user credentials to ensure both fields are filled.
     */
    private fun validateCredentials(user: UserLogin): Boolean {
        return user.userName.orEmpty().isNotEmpty() && user.userPassword.orEmpty().isNotEmpty()
    }
    /**
     * Emits UI effects such as error messages or navigation events.
     */
    private fun sendEffect(effect: LoginUiEffect) {
        viewModelScope.launch {
            _effect.emit(effect) // Ensures the effect is emitted properly
        }
    }

}