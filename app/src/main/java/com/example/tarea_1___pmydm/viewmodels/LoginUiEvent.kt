package com.example.tarea_1___pmydm.viewmodels

sealed interface LoginUiEvent {
    object NavigateToHome : LoginUiEvent
    data class ShowError(val messageResId: Int) : LoginUiEvent
}
