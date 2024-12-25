package com.example.curlymessenger.view

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.curlymessenger.R
import com.example.curlymessenger.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var prg :ProgressBar
    lateinit var email:String
    lateinit var pass:String
   // lateinit private var firauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //add logic fairbase

//btn login

        binding.btnlogin.setOnClickListener{

        }

    }
//logic singin
    private fun signIn(){

    }
}