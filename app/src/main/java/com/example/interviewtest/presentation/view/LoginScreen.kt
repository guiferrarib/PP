package com.example.interviewtest.presentation.view

import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtest.presentation.model.LoginUiEffect
import com.example.interviewtest.presentation.model.LoginUiEvent
import com.example.interviewtest.presentation.model.UserLogin
import com.example.interviewtest.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen() {
    val userEmail = rememberTextFieldState("")
    val userPassword = rememberTextFieldState("")
    val context = LocalContext.current

    val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
        context.applicationContext as android.app.Application
    ).create(LoginViewModel::class.java)

    val state = viewModel.state.collectAsState()
    val effect = viewModel.effect.collectAsState()

    LaunchedEffect(state.value) {

    }

    LaunchedEffect(effect.value) {
        when(effect.value){
            LoginUiEffect.InitialState -> {}
            is LoginUiEffect.NavigateToHome -> {
                Toast.makeText(context, "Login Successfull", Toast.LENGTH_SHORT).show()
            }
            is LoginUiEffect.ShowError -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }



    Column {
        //EditText fields for username and password
        //Login button
        TextField(
            value = userEmail.text.toString(),
            onValueChange = { userEmail.setTextAndPlaceCursorAtEnd(it) },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = userPassword.text.toString(),
            onValueChange = { userPassword.setTextAndPlaceCursorAtEnd(it) },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { //navigate the user to a welcome screen displaying a welcome message}

                viewModel.onEvent(
                    LoginUiEvent.OnLoginClicked(
                        UserLogin(
                            userEmail.text.toString(),
                            userPassword.text.toString()
                        )
                    )
                )
            },
        ) {
            Text("Login")
        }
    }
}