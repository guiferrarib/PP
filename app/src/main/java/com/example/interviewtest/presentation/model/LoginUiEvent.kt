package com.example.interviewtest.presentation.model

sealed class LoginUiEvent {
    data class OnLoginClicked(val user: UserLogin) : LoginUiEvent()
    data object OnLoginSuccess : LoginUiEvent()
    data object OnLoginFailed : LoginUiEvent()
    data object OnLoginLoading : LoginUiEvent()
    data object OnLoginError : LoginUiEvent()
}