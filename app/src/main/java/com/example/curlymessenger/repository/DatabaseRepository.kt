package com.example.curlymessenger.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class DatabaseRepository {

    companion object {
        private const val USERS = "users"
        private const val CHATS = "chats"
        private const val PARTICIPANTS = "participants"
    }

    private val db = Firebase.firestore

    /**
     * All registered users of the app.
     */
    var users: MutableLiveData<List<User>> = MutableLiveData(listOf())
        private set

    /**
     * The current FirebaseUser's active chats.
     */
    var activeChats: MutableLiveData<List<Chat>> = MutableLiveData(listOf())
        private set


    init {
        addAllUsersSnapshotListener()
    }

    //TODO Remove user?
    //TODO Change nickname?

    fun addUserToDatabase(user: User) {
        db.collection(USERS).document(user.id).set(user)
    }

    fun startNewChat(
        participants: List<User>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val chatDocument = db.collection(CHATS).document()
        val chat = Chat(chatDocument.id, participants)
        chatDocument.set(chat).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    fun getActiveChats(currentUserId: String) {
        db.collection(CHATS).whereArrayContains(PARTICIPANTS, currentUserId)
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


    private fun addAllUsersSnapshotListener() {
        db.collection(USERS).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.i("Fetching users error:", error.message.toString())
            } else {
                snapshot?.let {
                    val tempList = users.value?.toMutableList()
                    for (document in snapshot) {
                        val user = document.toObject<User>()
                        tempList?.add(user)
                        users.value = tempList
                    }
                }
            }
        }
    }
}