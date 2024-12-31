package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.User
import com.example.curlymessenger.repository.AuthRepository
import com.example.curlymessenger.repository.DatabaseRepository
import com.google.firebase.auth.FirebaseUser

class ChatViewModel : ViewModel() {

    private val dbRepo = DatabaseRepository()
    private val authRepo = AuthRepository()


    val activeChats: LiveData<List<Chat>> get() = dbRepo.activeChats
    private val currentUser: LiveData<FirebaseUser> get() = authRepo.currentUser

    private val currentUserObserver: (value: FirebaseUser) -> Unit = { firebaseUser ->
        dbRepo.getActiveChats(firebaseUser.uid)
    }

    init {
        currentUser.observeForever(currentUserObserver)
    }

    fun startNewChat(
        participants: List<User>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        dbRepo.startNewChat(participants, onSuccess, onFailure)
    }

    override fun onCleared() {
        super.onCleared()
        currentUser.removeObserver(currentUserObserver)
    }
}