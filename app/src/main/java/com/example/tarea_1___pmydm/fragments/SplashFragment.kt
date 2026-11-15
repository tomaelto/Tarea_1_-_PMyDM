package com.example.tarea_1___pmydm.fragments

import com.example.tarea_1___pmydm.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tarea_1___pmydm.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Al pulsar Iniciar Sesion, pasa a la pantalla de login
        binding.splashBtnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

        // // Al pulsar Regístrate, pasa a la pantalla de registro
        binding.splashBtnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_registroFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
