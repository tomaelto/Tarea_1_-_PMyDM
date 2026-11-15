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
import com.example.tarea_1___pmydm.databinding.FragmentRegistroBinding
import com.example.tarea_1___pmydm.viewmodels.RegistroUiEvent
import com.example.tarea_1___pmydm.viewmodels.RegistroViewModel
import kotlinx.coroutines.launch

class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.textNombre.error = state.nombreError?.let { getString(it) }
                        binding.textCorreo.error = state.emailError?.let { getString(it) }
                        binding.textContrasena.error = state.passwordError?.let { getString(it) }
                        binding.textTelefono.error = state.telefonoError?.let { getString(it) }
                        binding.btnRegistrate.isEnabled = state.isRegistroEnabled
                    }
                }
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is RegistroUiEvent.NavigateToLogin ->
                                findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
                            is RegistroUiEvent.ShowError ->
                                Toast.makeText(requireContext(), getString(event.messageResId), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Cada letra que se escribe o se borra avisa al ViewModel
        binding.nombreRegister.doOnTextChanged { text, _, _, _ ->
            viewModel.onNombreChanged(text.toString())
        }
        binding.correoRegister.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }
        binding.contrasenaRegister.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }
        binding.telefonoRegister.doOnTextChanged { text, _, _, _ ->
            viewModel.onTelefonoChanged(text.toString())
        }

        // Al pulsar Regístrate, el ViewModel comprueba los datos
        binding.btnRegistrate.setOnClickListener {
            viewModel.onRegistroClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
