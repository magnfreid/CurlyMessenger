package com.example.curlymessenger.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class AuthRepository {

    private val auth = Firebase.auth

    private val authStateListener: FirebaseAuth.AuthStateListener = FirebaseAuth.AuthStateListener {
        currentUser.postValue(it.currentUser)
    }

    var currentUser: MutableLiveData<FirebaseUser> = MutableLiveData(auth.currentUser)
        private set

    init {
        auth.addAuthStateListener(authStateListener)
    }

    fun signInWithEmailAndPassword(
        email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
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
            }
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    //TODO Make sure this is run to avoid memory leaks
    fun onClear() {
        auth.removeAuthStateListener(authStateListener)
    }


}