package com.avilanii.attend.features.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.core.domain.models.Email
import com.avilanii.attend.core.domain.models.Password
import com.avilanii.attend.features.auth.presentation.models.UserLoginRequestUi
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun LoginScreen(
    onAction: (LoginScreenAction)->Unit,
    modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        var email by rememberSaveable { mutableStateOf("") }
        var isEmailInvalid by rememberSaveable { mutableStateOf(false) }
        var password by rememberSaveable { mutableStateOf("") }
        var isPasswordInvalid by rememberSaveable { mutableStateOf(false) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(paddingValues).fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Login icon",
                modifier = modifier.padding(8.dp)
            )
            Text(
                text = "Log into your account",
                style = MaterialTheme.typography.headlineSmall
            )
            OutlinedTextField(
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                },
                label = {
                    Text("Email")
                }
            )
            if(isEmailInvalid){
                Text("Please enter a valid email!", color = MaterialTheme.colorScheme.error)
            }
            OutlinedTextField(
                value = password,
                onValueChange = { newValue ->
                    password = newValue
                },
                label = {
                    Text("Password")
                }
            )
            if(isPasswordInvalid){
                Text("Password can't be null!", color = MaterialTheme.colorScheme.error)
            }
            Button(onClick = {
                try {
                    Email(email)
                    Password(password)
                    onAction(LoginScreenAction.OnSubmitForm(
                        userLoginRequestUi = UserLoginRequestUi(email, password)
                    ))
                } catch (e: IllegalArgumentException){
                    isEmailInvalid = true
                    isPasswordInvalid = true
                    e.printStackTrace()
                }
            }) { Text("Login") }
            Row (modifier = modifier.padding(paddingValues), horizontalArrangement = Arrangement.spacedBy(15.dp)){
                Text("New here?")
                Text("Register an account", color = MaterialTheme.colorScheme.primary,modifier = Modifier.clickable{
                    onAction(LoginScreenAction.OnRegisterClick)
                })
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoginScreen() {
    AttenDTheme {
        LoginScreen(
            onAction = {}
        )
    }
}