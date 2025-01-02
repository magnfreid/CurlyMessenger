package com.example.curlymessenger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.curlymessenger.repository.UserRepository

class AuthViewModel : ViewModel() {
    private val userRepo = UserRepository()

    fun signInWithEmailAndPassword(
        email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) {
        userRepo.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        nickname: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        userRepo.registerWithEmailAndPassword(email,
            password,
            nickname,
            { onSuccess() },
            { onFailure(it) })

    }
}