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

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, emailError = validateEmail(email)) }
        comprobarFormulario()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, passwordError = validatePassword(password)) }
        comprobarFormulario()
    }

    fun onLoginClicked() {
        val state = _uiState.value
        viewModelScope.launch {
            if (state.email == "tomas@gmail.com" && state.password == "tomas") {
                _uiEvent.send(LoginUiEvent.NavigateToHome)
            } else {
                _uiEvent.send(LoginUiEvent.ShowError(R.string.error_invalid_credentials))
            }
        }
    }

    // El botón se activa solo si los dos campos están rellenos y sin errores
    private fun comprobarFormulario() {
        _uiState.update {
            it.copy(isLoginEnabled = it.email.isNotBlank() && it.password.isNotBlank() &&
                    it.emailError == null && it.passwordError == null)
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
}
