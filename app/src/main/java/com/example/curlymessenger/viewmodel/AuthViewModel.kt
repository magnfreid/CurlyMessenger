package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    val currentUser: LiveData<FirebaseUser> get() = authRepository.currentUser

    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        authRepository.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun registerWithEmailAndPassword(email: String, password: String, onSuccess: (FirebaseUser) -> Unit, onFailure: (Exception) -> Unit){
        authRepository.registerWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun loginWithGoogleAccount(email:String) {
        //TODO implement Google sign in
    }
}