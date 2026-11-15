package com.example.tarea_1___pmydm

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
import com.example.tarea_1___pmydm.databinding.FragmentFourthBinding
import com.example.tarea_1___pmydm.viewmodels.RecuperarViewModel
import kotlinx.coroutines.launch

class Fourth_Fragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecuperarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
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
            findNavController().navigate(R.id.action_fourthFragment_to_secondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
