package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val currentUser: LiveData<FirebaseUser> get() = authRepository.currentUser

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepository.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepository.registerWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun loginWithGoogleAccount(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepository.signInWithGoogleAccount(email, onSuccess, onFailure)
    }
}