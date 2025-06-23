package com.avilanii.attend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.avilanii.attend.core.navigation.ApplicationRootComposable
import com.avilanii.attend.ui.theme.AttenDTheme

object SessionManager {
    var jwtToken: String? = null

    fun setToken(token: String?) {
        jwtToken = token
    }

    fun clearToken() {
        jwtToken = null
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AttenDTheme {
                ApplicationRootComposable()
            }
        }
    }
}