package com.example.tarea_1___pmydm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tarea_1___pmydm.databinding.FragmentSecondBinding

class Second_Fragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Al pulsar Regístrate, pasa a la pantalla de registro
        binding.loginBtnRegistrate.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
        }

        // Al pulsar "No recuerdo mi contraseña", pasa a la pantalla de recuperación de contraseña
        binding.loginBtnNorecuerdocontrasena.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_fourthFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
