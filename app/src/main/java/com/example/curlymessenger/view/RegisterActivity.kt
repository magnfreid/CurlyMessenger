package com.example.curlymessenger.view

import android.content.Intent
import android.view.View
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.curlymessenger.R
import com.example.curlymessenger.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prg: ProgressBar
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confPass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prg = binding.progressBar

        binding.textacont.setOnClickListener {
            // Navigate to LoginActivity
            // startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttloginup.setOnClickListener {
            name = binding.editname.text.toString().trim()
            email = binding.editEmailAddress.text.toString().trim()
            password = binding.editPassword.text.toString().trim()
            confPass = binding.editPassConfir.text.toString().trim()

            // Validate input fields
            when {
                name.isEmpty() -> showToast("Please enter a name")
                email.isEmpty() -> showToast("Please enter an email")
                password.isEmpty() -> showToast("Please enter a password")
                confPass.isEmpty() -> showToast("Please confirm your password")
                password != confPass -> showToast("Passwords do not match")
                else -> creatAccount(name, email, password, confPass)
            }
        }
    }

    private fun creatAccount(name: String, email: String, password: String, confPass: String) {
        // Show ProgressBar
        prg.visibility = View.VISIBLE


        //  Firebase Auth here for actual account creation
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                // Hide ProgressBar
                prg.visibility = View.GONE
                if (task.isSuccessful) {
                    showToast("Account created successfully")
                    // Navigate to another activity
                    // startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    showToast("Failed to create account: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}