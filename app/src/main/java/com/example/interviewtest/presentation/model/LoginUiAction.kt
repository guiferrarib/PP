package com.example.interviewtest.presentation.model

sealed class LoginUiAction {
    data class OnLoginSuccess(val user: UserLogin) : LoginUiAction()
    data class OnLoginError(val message: String) : LoginUiAction()
}