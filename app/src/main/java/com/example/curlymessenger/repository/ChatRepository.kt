package com.example.curlymessenger.repository

import androidx.lifecycle.MutableLiveData
import com.example.curlymessenger.model.PATH_CHATS
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.PATH_PARTICIPANTS
import com.example.curlymessenger.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class ChatRepository {
    private val db = Firebase.firestore

    var activeChats: MutableLiveData<List<Chat>> = MutableLiveData(listOf())
        private set

    /**
     * Sets the snapshot listener for active chats for the user that is currently logged in.
     */
    fun setActiveChatsSnapshotListener(currentUserId: String) {
        db.collection(PATH_CHATS).whereArrayContains(PATH_PARTICIPANTS, currentUserId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //TODO Error handling
                    return@addSnapshotListener
                }
                snapshot?.let {
                    activeChats.value = it.toObjects<Chat>()
                }
            }
    }

    /**
     * Creates a new chat in the database.
     */
    fun startNewChat(
        participants: List<User>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val chatDocument = db.collection(PATH_CHATS).document()
        val chat = Chat(chatDocument.id, participants)
        chatDocument.set(chat).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }
}