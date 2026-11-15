package com.example.tarea_1___pmydm.fragments

import com.example.tarea_1___pmydm.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarea_1___pmydm.databinding.FragmentRecuperarContrasenaBinding
import com.example.tarea_1___pmydm.viewmodels.RecuperarViewModel
import kotlinx.coroutines.launch

class RecuperarContrasenaFragment : Fragment() {

    private var _binding: FragmentRecuperarContrasenaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecuperarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecuperarContrasenaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textFgpasswCorreo.error = state.emailError?.let { getString(it) }
                    binding.btnFgpasswValidacion.isEnabled = state.isRecuperarEnabled
                }
            }
        }

        // Cada letra que se escribe o se borra avisa al ViewModel
        binding.texteditFgpasswCorreo.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        // Al pulsar el botón de validación, vuelve a la pantalla de Login
        binding.btnFgpasswValidacion.setOnClickListener {
            findNavController().navigate(R.id.action_recuperarContrasenaFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
