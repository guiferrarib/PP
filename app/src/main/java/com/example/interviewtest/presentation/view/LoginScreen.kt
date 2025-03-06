package com.example.interviewtest.presentation.view

import android.app.Application
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
/**
 * Composable function for the Login screen.
 * Provides input fields for username and password, and a login button.
 */
@Composable
fun LoginScreen() {
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val context = LocalContext.current
    // Obtain ViewModel instance
    val viewModel = ViewModelProvider.AndroidViewModelFactory
        .getInstance(context.applicationContext as Application)
        .create(LoginViewModel::class.java)

    var errorMessage by remember { mutableStateOf("") }

    // Collect UI effects such as navigation or error messages
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LoginUiEffect.NavigateToHome -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }
                is LoginUiEffect.ShowError -> {
                    errorMessage = effect.message
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                // Username input field
                TextField(
                    value = userEmail.value,
                    onValueChange = { userEmail.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorMessage.isNotEmpty()
                )
                // Password input field
                TextField(
                    value = userPassword.value,
                    onValueChange = { userPassword.value = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorMessage.isNotEmpty()
                )
                // Error message display
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }
                // Login button
                Button(
                    onClick = {
                        errorMessage = ""
                        viewModel.onEvent(
                            LoginUiEvent.OnLoginClicked(
                                UserLogin(
                                    userEmail.value,
                                    userPassword.value
                                )
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        }
    }
}