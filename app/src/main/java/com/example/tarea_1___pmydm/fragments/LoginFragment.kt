package com.example.tarea_1___pmydm.fragments

import com.example.tarea_1___pmydm.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarea_1___pmydm.databinding.FragmentLoginBinding
import com.example.tarea_1___pmydm.viewmodels.LoginUiEvent
import com.example.tarea_1___pmydm.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Al pulsar Regístrate, pasa a la pantalla de registro
        binding.loginBtnRegistrate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        // Al pulsar "No recuerdo mi contraseña", pasa a la pantalla de recuperación de contraseña
        binding.loginBtnNorecuerdocontrasena.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperarContrasenaFragment)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.textCorreoElectronico.error = state.emailError?.let { getString(it) }
                        binding.loginLayoutContrasena.error = state.passwordError?.let { getString(it) }
                        binding.loginBtnAcceder.isEnabled = state.isLoginEnabled
                    }
                }
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is LoginUiEvent.NavigateToHome ->
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            is LoginUiEvent.ShowError ->
                                Toast.makeText(requireContext(), getString(event.messageResId), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Cada letra que se escribe o se borra avisa al ViewModel
        binding.loginCorreoElectronico.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }
        binding.loginEditTextContrasena.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }

        // Al pulsar "acceder", el ViewModel comprueba los datos
        binding.loginBtnAcceder.setOnClickListener {
            viewModel.onLoginClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
