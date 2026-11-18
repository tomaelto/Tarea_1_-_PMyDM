package com.example.tarea_1___pmydm.viewmodels

sealed interface RegistroUiEvent {
    object NavigateToLogin : RegistroUiEvent
    data class ShowError(val messageResId: Int) : RegistroUiEvent
}
