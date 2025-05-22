package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var token by remember { mutableStateOf("Cargando token...") }

            // Obtener el token
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                    Log.d("FCM", "Token: $token")
                } else {
                    token = "Error al obtener el token"
                    Log.w("FCM", "Fetching FCM token failed", task.exception)
                }
            }

            // Mostrar en pantalla
            MaterialTheme {
                Surface {
                    Text(text = token)
                }
            }
        }
    }
}
