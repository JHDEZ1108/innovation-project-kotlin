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
import com.g05.innovationprojectgt01_05.data.entities.UserEntity
import com.g05.innovationprojectgt01_05.ui.theme.InnovationProjectGT0105Theme
import com.g05.innovationprojectgt01_05.ui.viewmodel.UserViewModel
import com.g05.innovationprojectgt01_05.ui.viewmodel.UserViewModelFactory
import com.g05.innovationprojectgt01_05.ui.screens.HomeActivity

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(applicationContext))

            InnovationProjectGT0105Theme {
                RegisterScreen(
                    userViewModel = userViewModel,
                    onRegisterSuccess = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onRegisterError = { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    },
                    onNavigateToLogin = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    userViewModel: UserViewModel,
    onRegisterSuccess: () -> Unit,
    onRegisterError: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    val isFormValid = username.isNotBlank() && email.isNotBlank() &&
            password.isNotBlank() && confirmPassword.isNotBlank()

    val passwordMismatch = password != confirmPassword && confirmPassword.isNotBlank()

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
            Text("Crear cuenta", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    val icon = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(imageVector = icon, contentDescription = "Mostrar/ocultar contraseña")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(imageVector = icon, contentDescription = "Mostrar/ocultar contraseña")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = passwordMismatch,
                supportingText = {
                    if (passwordMismatch) Text(
                        "Las contraseñas no coinciden",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isFormValid) {
                        onRegisterError("Completa todos los campos")
                        return@Button
                    }

                    if (password.length < 6) {
                        onRegisterError("La contraseña debe tener al menos 6 caracteres")
                        return@Button
                    }

                    if (passwordMismatch) {
                        onRegisterError("Las contraseñas no coinciden")
                        return@Button
                    }

                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val firebaseUser = result.user
                            if (firebaseUser != null) {
                                val uid = firebaseUser.uid
                                val newUser = UserEntity(
                                    firebaseUid = uid,
                                    username = username,
                                    email = email
                                )
                                userViewModel.insertUser(newUser)
                            }

                            onRegisterSuccess()
                        }
                        .addOnFailureListener { e ->
                            onRegisterError("Error al registrar: ${e.message}")
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text("¿Ya tienes cuenta? Iniciar sesión")
            }
        }
    }
}
