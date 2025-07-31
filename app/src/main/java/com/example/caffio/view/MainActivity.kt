package com.example.caffio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.caffio.ui.theme.CaffioTheme
import com.example.caffio.view.FavoriteScreen
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            CaffioTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    FavoriteScreen()
                }
            }
        }
    }
}
