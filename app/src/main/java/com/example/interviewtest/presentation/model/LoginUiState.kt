package com.example.interviewtest.presentation.model

data class LoginUiState(
    val userLogin: UserLogin? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)