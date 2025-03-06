package com.example.interviewtest.presentation.model

data class LoginUiState(
    val userLogin: UserLogin? = null,
    val isLoading: Boolean? = null,
    val error: String? = null,
    val isLoginSuccess: Boolean? = null,
    val isLoginFailed: Boolean? = null,
    val isLoginButtonEnabled: Boolean? = null,
)