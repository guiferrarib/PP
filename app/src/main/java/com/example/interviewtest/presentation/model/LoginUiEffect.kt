package com.example.interviewtest.presentation.model

sealed class LoginUiEffect {
    data class NavigateToHome(val userLogin: UserLogin) : LoginUiEffect()
    data class ShowError(val message: String) : LoginUiEffect()
}