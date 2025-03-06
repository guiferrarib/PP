package com.example.interviewtest.presentation.view

import android.app.Application
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtest.presentation.model.LoginUiEffect
import com.example.interviewtest.presentation.model.LoginUiEvent
import com.example.interviewtest.presentation.model.UserLogin
import com.example.interviewtest.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen() {
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val context = LocalContext.current

    val viewModel = ViewModelProvider.AndroidViewModelFactory
        .getInstance(context.applicationContext as Application)
        .create(LoginViewModel::class.java)

    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(LoginUiEffect.InitialState)

    LaunchedEffect(effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LoginUiEffect.InitialState -> {
                    // Do nothing
                }

                is LoginUiEffect.NavigateToHome -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }

                is LoginUiEffect.ShowError -> {
                    Toast.makeText(
                        context,
                        "Error: ${(effect as LoginUiEffect.ShowError).message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center // Mantém tudo centralizado
        ) {
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = userEmail.value,
                    onValueChange = { userEmail.value = it },
                    label = { Text("Email") }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = userPassword.value,
                    onValueChange = { userPassword.value = it },
                    label = { Text("Password") }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(
                            LoginUiEvent.OnLoginClicked(
                                UserLogin(
                                    userEmail.value,
                                    userPassword.value
                                )
                            )
                        )
                    },
                ) {
                    Text("Login")
                }
            }
        }
    }
}