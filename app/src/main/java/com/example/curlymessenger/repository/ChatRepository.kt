package com.example.curlymessenger.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.curlymessenger.model.PATH_CHATS
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.Message
import com.example.curlymessenger.model.PATH_MESSAGES
import com.example.curlymessenger.model.PATH_PARTICIPANTS_BY_ID
import com.example.curlymessenger.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class ChatRepository {
    private val db = Firebase.firestore

    var activeChats: MutableLiveData<List<Chat>> = MutableLiveData(listOf())
        private set
    var activeMessages: MutableLiveData<List<Message>> = MutableLiveData(listOf())

    /**
     * Sets the snapshot listener for active chats for the user that is currently logged in.
     */
    fun setActiveChatsSnapshotListener(currentUserId: String) {
        db.collection(PATH_CHATS).whereArrayContains(PATH_PARTICIPANTS_BY_ID, currentUserId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //TODO Error handling
                    return@addSnapshotListener
                }
                snapshot?.let {
                    Log.i("Snapshot:", snapshot.toString())
                    activeChats.value = it.toObjects<Chat>()
                }
            }
    }

    fun setActiveMessagesSnapshotListener(chatId: String) {
        db.collection(PATH_CHATS).document(chatId).collection(PATH_MESSAGES)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //TODO Error handling
                    return@addSnapshotListener
                }
                snapshot?.let {
                    activeMessages.value = snapshot.toObjects<Message>()
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
        val participantsById = mutableListOf<String>()
        for (user in participants) {
            participantsById.add(user.id)
        }
        val chat = Chat(chatDocument.id, participantsById, participants)
        chatDocument.set(chat).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    //TODO Handle errors
    fun sendMessage(chatId: String, message: Message) {
        val messageRef =
            db.collection(PATH_CHATS).document(chatId).collection(PATH_MESSAGES).document()
        message.id = messageRef.id
        messageRef.set(message)
    }
}