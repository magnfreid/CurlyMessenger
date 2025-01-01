package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.User
import com.example.curlymessenger.repository.UserRepository
import com.example.curlymessenger.repository.ChatRepository

class ChatViewModel : ViewModel() {

    private val chatRepo = ChatRepository()
    private val userRepo = UserRepository()


    val activeChats: LiveData<List<Chat>> = chatRepo.activeChats
    private val currentUser: LiveData<User?> get() = userRepo.currentUser

    private val currentUserObserver: (value: User?) -> Unit = { user ->
        user?.let {
            chatRepo.setActiveChatsSnapshotListener(user.id)
        }
    }

    init {
        currentUser.observeForever(currentUserObserver)
    }

    fun startNewChat(
        participants: List<User>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) {
        chatRepo.startNewChat(participants, onSuccess, onFailure)
    }

    override fun onCleared() {
        super.onCleared()
        currentUser.removeObserver(currentUserObserver)
    }
}