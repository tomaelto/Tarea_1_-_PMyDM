package com.example.tarea_1___pmydm.viewmodels

// Datos de la pantalla
data class RecuperarUiState(
    val email: String = "",
    val emailError: Int? = null,
    val isRecuperarEnabled: Boolean = false
)
