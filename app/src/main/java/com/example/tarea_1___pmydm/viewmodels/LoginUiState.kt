package com.example.tarea_1___pmydm.viewmodels

// Datos de la pantalla
data class LoginUiState(
    val email: String = "",
    val emailError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val isLoginEnabled: Boolean = false
)
