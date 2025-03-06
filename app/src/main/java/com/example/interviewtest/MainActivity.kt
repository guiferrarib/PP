package com.example.interviewtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewtest.presentation.view.LoginScreen
import com.example.interviewtest.ui.theme.InterViewTestTheme

//Description:
//Create a simple Android application with a login screen.
// The login screen should accept a username and password.
// Upon entering valid credentials, the user should be redirected to a welcome screen.
// Ensure that the login process is secure and follows best practices.
//Requirements:
//Create a layout for the login screen with EditText fields for username and password,
// and a login button.
//Implement validation to ensure that both username and password fields are not empty.
//Implement a secure authentication mechanism to verify the username and password.
// You can use hardcoded credentials for simplicity.
//After successful authentication,
// navigate the user to a welcome screen displaying a welcome message.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterViewTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InterViewTestTheme {
        Greeting("Android")
    }
}