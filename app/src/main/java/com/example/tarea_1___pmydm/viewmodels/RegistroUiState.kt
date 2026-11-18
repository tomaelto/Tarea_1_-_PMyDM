package com.example.tarea_1___pmydm.viewmodels

// Datos de la pantalla
data class RegistroUiState(
    val nombre: String = "",
    val nombreError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val telefono: String = "",
    val telefonoError: Int? = null,
    val isRegistroEnabled: Boolean = false
)
