package com.g05.innovationprojectgt01_05

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.g05.innovationprojectgt01_05.auth.LoginActivity
import com.g05.innovationprojectgt01_05.ui.screens.HomeActivity
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Places solo una vez
        if (!Places.isInitialized()) {
            Places.initialize(
                applicationContext,
                "AIzaSyCoZO3Os0MUCtX5WgFPHg9kVUSqSQwF6ik",
                resources.configuration.locales[0] // Usa el locale actual del dispositivo
            )
        }

        // Verifica si el usuario ya está autenticado
        val currentUser = Firebase.auth.currentUser

        if (currentUser != null) {
            // Usuario autenticado → ir a Home
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            // No autenticado → ir a Login
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish() // Cierra MainActivity para que no quede en el stack
    }
}
