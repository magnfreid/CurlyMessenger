package com.example.curlymessenger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.User
import com.example.curlymessenger.repository.AuthRepository
import com.example.curlymessenger.repository.DatabaseRepository

class AuthViewModel : ViewModel() {
    private val authRepo = AuthRepository()
    private val dbRepo = DatabaseRepository()

    fun signInWithEmailAndPassword(
        email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) {
        authRepo.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        nickname: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepo.registerWithEmailAndPassword(
            email,
            password,
            onSuccess = { authResult ->
                authResult.user?.let { firebaseUser ->
                    val newUser = User(firebaseUser.uid, nickname, firebaseUser.email)
                    dbRepo.addUserToDatabase(newUser)
                }
                onSuccess()
            },
            onFailure = onFailure
        )

    }
}