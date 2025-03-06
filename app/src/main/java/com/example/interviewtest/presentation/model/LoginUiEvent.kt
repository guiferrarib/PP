package com.example.interviewtest.presentation.model

sealed class LoginUiEvent {
    data class OnLoginClicked(val user: UserLogin) : LoginUiEvent()
}