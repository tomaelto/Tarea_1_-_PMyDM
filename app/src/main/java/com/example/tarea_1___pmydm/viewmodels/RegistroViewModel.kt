package com.example.tarea_1___pmydm.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea_1___pmydm.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Datos de la pantalla (el ViewModel los conserva al girar la pantalla)
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

// Eventos de un solo uso
sealed interface RegistroUiEvent {
    object NavigateToLogin : RegistroUiEvent
    data class ShowError(val messageResId: Int) : RegistroUiEvent
}

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RegistroUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNombreChanged(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, nombreError = validateNombre(nombre)) }
        comprobarFormulario()
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, emailError = validateEmail(email)) }
        comprobarFormulario()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, passwordError = validatePassword(password)) }
        comprobarFormulario()
    }

    fun onTelefonoChanged(telefono: String) {
        _uiState.update { it.copy(telefono = telefono, telefonoError = validateTelefono(telefono)) }
        comprobarFormulario()
    }

    fun onRegistroClicked() {
        val state = _uiState.value
        viewModelScope.launch {
            if (state.email == "admin@test.com") {
                _uiEvent.send(RegistroUiEvent.ShowError(R.string.error_email_already_registered))
            } else {
                _uiEvent.send(RegistroUiEvent.NavigateToLogin)
            }
        }
    }

    // El botón se activa solo si todos los campos están rellenos y sin errores
    private fun comprobarFormulario() {
        _uiState.update {
            it.copy(isRegistroEnabled =
                it.nombre.isNotBlank() && it.email.isNotBlank() &&
                it.password.isNotBlank() && it.telefono.isNotBlank() &&
                it.nombreError == null && it.emailError == null &&
                it.passwordError == null && it.telefonoError == null)
        }
    }

    private fun validateNombre(nombre: String): Int? {
        return when {
            nombre.isEmpty() -> null
            nombre.length < 3 -> R.string.error_nombre_too_short
            else -> null
        }
    }

    private fun validateEmail(email: String): Int? {
        return when {
            email.isEmpty() -> null
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_invalid_email
            else -> null
        }
    }

    private fun validatePassword(password: String): Int? {
        return when {
            password.isEmpty() -> null
            password.length < 5 -> R.string.error_password_too_short
            else -> null
        }
    }

    private fun validateTelefono(telefono: String): Int? {
        return when {
            telefono.isEmpty() -> null
            telefono.length != 9 -> R.string.error_invalid_telefono
            else -> null
        }
    }
}
