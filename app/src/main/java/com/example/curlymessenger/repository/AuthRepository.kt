package com.example.curlymessenger.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class AuthRepository {

    private val auth = Firebase.auth

    var currentUser: MutableLiveData<FirebaseUser> = MutableLiveData(null)
        private set

    init {
        currentUser.value = auth.currentUser
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
            val user = authResult.user
            user?.let {
                onSuccess(authResult)
            } ?: onFailure(Exception("User ID error."))
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    fun signInWithGoogleAccount(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        //TODO implement Google login
    }
}