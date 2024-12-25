package com.example.curlymessenger.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.curlymessenger.R
import com.example.curlymessenger.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prg: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001 // Unique request code for Google Sign-In

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        prg = binding.progressBar2

        // Configure Google Sign-In
        val googleSignInOptions = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            // .requestIdToken(getString(R.string.default_web_client_id))  // Set your web client ID this is in firbase
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Google Sign-In button click
        binding.imagbtngoogle.setOnClickListener {
            signInWithGoogle()
        }

        // Regular Email Registration logic
        binding.buttloginup.setOnClickListener {
            val name = binding.editname.text.toString().trim()
            val email = binding.editEmailAddress.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            val confPass = binding.editPassConfir.text.toString().trim()

            when {
                name.isEmpty() -> showToast("Please enter a name")
                email.isEmpty() -> showToast("Please enter an email")
                password.isEmpty() -> showToast("Please enter a password")
                confPass.isEmpty() -> showToast("Please confirm your password")
                password != confPass -> showToast("Passwords do not match")
                else -> createAccount(name, email, password)
            }
        }
    }

    private fun createAccount(name: String, email: String, password: String) {
        prg.visibility = View.VISIBLE

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                prg.visibility = View.GONE
                if (task.isSuccessful) {
                    showToast("Account created successfully")
                    // startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    showToast("Failed to create account: ${task.exception?.message}")
                }
            }
    }

    // Google Sign-In logic
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                showToast("Google Sign-In failed: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        prg.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                prg.visibility = View.GONE
                if (task.isSuccessful) {
                    showToast("Google Sign-In successful")
                    // Navigate to another activity (e.g., Home screen)
                    // startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    showToast("Google Sign-In failed: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
