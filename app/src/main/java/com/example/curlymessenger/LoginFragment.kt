package com.example.curlymessenger

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.curlymessenger.databinding.FragmentLoginBinding
import com.example.curlymessenger.view.MainActivity
import com.example.curlymessenger.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var et_email : EditText
    private lateinit var et_pass : EditText
    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle ?) :
            View{
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_email = binding.etEmail
        et_pass = binding.etPassword

        binding.btnLogin.setOnClickListener {
            val email = et_email.text.toString()
            val pass = et_pass.text.toString()

            if (loginValid(email, pass)) {
                authViewModel.signInWithEmailAndPassword(email, pass,
                    onSuccess = {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    },
                    onFailure = { exception ->
                        Toast.makeText(requireContext(), "Failed to login: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText( requireContext(), "Wrong Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun loginValid(email : String, password : String) : Boolean{
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPassValid = password.length >= 4

        return isEmailValid && isPassValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    }
