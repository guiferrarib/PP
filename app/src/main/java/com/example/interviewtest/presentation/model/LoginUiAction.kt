package com.example.interviewtest.presentation.model

sealed class LoginUiAction {
    data class OnLoginClicked(val user: UserLogin) : LoginUiAction()
    data object OnLoginSuccess : LoginUiAction()
    data object OnLoginFailed : LoginUiAction()
    data object OnLoginLoading : LoginUiAction()
    data class OnLoginError(val message: String) : LoginUiAction()
}