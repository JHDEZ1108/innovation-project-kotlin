package com.g05.innovationprojectgt01_05.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.g05.innovationprojectgt01_05.ui.screens.HomeActivity
import com.g05.innovationprojectgt01_05.ui.theme.InnovationProjectGT0105Theme
import com.g05.innovationprojectgt01_05.ui.viewmodel.UserViewModel
import com.g05.innovationprojectgt01_05.ui.viewmodel.UserViewModelFactory

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(applicationContext))

            InnovationProjectGT0105Theme {
                LoginScreen(
                    userViewModel = userViewModel,
                    onLoginSuccess = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onLoginError = { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    },
                    onNavigateToRegister = {
                        startActivity(Intent(this, RegisterActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onLoginError: (String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val isFormValid = email.isNotBlank() && password.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        val icon = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        Icon(imageVector = icon, contentDescription = "Mostrar/ocultar contraseña")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isFormValid) {
                        onLoginError("Completa todos los campos")
                        return@Button
                    }

                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val firebaseUser = result.user
                            if (firebaseUser != null) {
                                val uid = firebaseUser.uid
                                val emailFromFirebase = firebaseUser.email ?: ""
                                val usernameFromEmail = emailFromFirebase.substringBefore("@")

                                // Insert only if not exists in local DB
                                userViewModel.insertUserIfNotExists(
                                    uid = uid,
                                    username = usernameFromEmail,
                                    email = emailFromFirebase
                                )
                            }

                            onLoginSuccess()
                        }
                        .addOnFailureListener { e ->
                            val errorMessage = if (
                                e.message?.contains("no user record", ignoreCase = true) == true
                            ) "El usuario no existe"
                            else "Error al iniciar sesión: ${e.message}"

                            onLoginError(errorMessage)
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Crear cuenta")
            }
        }
    }
}
