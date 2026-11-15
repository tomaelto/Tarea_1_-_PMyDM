package com.example.tarea_1___pmydm.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.tarea_1___pmydm.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Datos de la pantalla
data class RecuperarUiState(
    val email: String = "",
    val emailError: Int? = null,
    val isRecuperarEnabled: Boolean = false
)

class RecuperarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecuperarUiState())
    val uiState: StateFlow<RecuperarUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        val error = if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            R.string.error_invalid_email else null

        _uiState.update {
            it.copy(email = email, emailError = error,
                isRecuperarEnabled = email.isNotBlank() && error == null)
        }
    }
}
