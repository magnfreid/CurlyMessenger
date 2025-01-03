package com.example.curlymessenger.view

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.curlymessenger.R
import com.example.curlymessenger.databinding.FragmentLoginBinding
import com.example.curlymessenger.databinding.FragmentRegisterBinding
import com.example.curlymessenger.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    var _binding : FragmentRegisterBinding? = null
    private val authViewModel : AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.findViewById<MaterialButton>(R.id.btn_login_register).setOnClickListener(){
            val email = view.findViewById<EditText>(R.id.et_email_register).text.toString()
            val pass = view.findViewById<EditText>(R.id.et_password_register).text.toString()
            val nick = view.findViewById<EditText>(R.id.et_nickname_register).text.toString()

            if (loginValid(email, pass)) {
                authViewModel.registerWithEmailAndPassword(email, pass, nick,
                    onSuccess = {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    },
                    onFailure = { exception ->
                        Toast.makeText(requireContext(), "Failed to login: ${exception.message}", Toast.LENGTH_SHORT).show()
                        Log.i("!!!", "${email}")
                    }
                )
            } else {
                Toast.makeText( requireContext(), "Register account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginValid(email : String, password : String) : Boolean{
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPassValid = password.length >= 4

        return isEmailValid && isPassValid
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


}