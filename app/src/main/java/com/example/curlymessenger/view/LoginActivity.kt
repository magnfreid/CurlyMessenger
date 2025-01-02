package com.example.curlymessenger.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.curlymessenger.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment() {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, RegisterFragment())
                        .commit()
                })
                .commit()
        }

    }
}

