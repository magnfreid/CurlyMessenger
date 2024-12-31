package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.repository.AuthRepository
import com.example.curlymessenger.repository.FirestoreRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class MainViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val databaseRepository = FirestoreRepository()

    //Observer
    private val currentUserObserver: (FirebaseUser?) -> Unit = {
        it?.let {
            databaseRepository.addActiveChatsSnapshotListener(it)
        }
    }

    init {
        currentUser.observeForever(currentUserObserver)
    }

    val currentUser: LiveData<FirebaseUser> get() = authRepository.currentUser
    val activeChats: LiveData<List<Chat>> get() = databaseRepository.activeChats

    fun signInWithEmailAndPassword(
        email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) {
        authRepository.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        nickname: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepository.registerWithEmailAndPassword(email, password, onSuccess = { authResult ->
            authResult.user?.let { firebaseUser ->
                databaseRepository.addUserToDatabase(firebaseUser, nickname)
            }
            onSuccess()
        }, onFailure = onFailure)

    }

    fun loginWithGoogleAccount(
        email: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) {
        authRepository.signInWithGoogleAccount(email, onSuccess, onFailure)
    }

    override fun onCleared() {
        super.onCleared()
        currentUser.removeObserver(currentUserObserver)
    }
}