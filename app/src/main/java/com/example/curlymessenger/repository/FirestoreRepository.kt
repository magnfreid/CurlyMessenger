package com.example.curlymessenger.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.ChatReference
import com.example.curlymessenger.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class FirestoreRepository {

    companion object {
        const val ACTIVE_CHATS = "activeChats"
        const val ACTIVE_CHATS_REFERENCES = "activeChatsReferences"
        const val CHATS = "chats"
        const val USERS = "users"
    }

    private val db = Firebase.firestore

    /**
     * All registered users of the app.
     */
    var allUsers: MutableLiveData<List<User>> = MutableLiveData(listOf())
        private set

    /**
     * The current FirebaseUser's active chats.
     */
    var activeChats: MutableLiveData<List<Chat>> = MutableLiveData(listOf())
        private set

    private var activeConversationsReferences: MutableList<ChatReference> = mutableListOf()


    init {
        addAllUsersSnapshotListener()
    }

    //TODO Remove user?
    //TODO Change nickname?

    fun addUser(firebaseUser: FirebaseUser, nickname: String) {
        val id = firebaseUser.uid
        val newUser = User(id, nickname, firebaseUser.email)
        db.collection(USERS).document(id).set(newUser)
    }


    //TODO Handle onSuccess and onFailure for both CRUD operations (use a list of errors?)
    fun startNewChat(
        participants: List<User>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        //Add conversation to conversations collection
        val chatDocument = db.collection(CHATS).document()
        val chat = Chat(chatDocument.id, participants)
        chatDocument.set(chat).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
        //Add a conversation reference to each participant's list of active conversations
        for (user in participants) {
            val conversationReference = ChatReference(chat.id, participants)
            db.collection(USERS).document(user.id).collection(ACTIVE_CHATS).document(chat.id)
                .set(conversationReference)
        }
    }

    private fun addActiveChatsReferencesSnapshotListener(
        currentUser: FirebaseUser, onComplete: () -> Unit
    ) {
        db.collection(USERS).document(currentUser.uid).collection(ACTIVE_CHATS_REFERENCES)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.i(
                        "Fetching active conversations references error:", error.message.toString()
                    )
                } else {
                    val tempList = mutableListOf<ChatReference>()
                    snapshot?.let {
                        for (document in snapshot) {
                            val chatReference = document.toObject<ChatReference>()
                            tempList.add(chatReference)
                        }
                        activeConversationsReferences = tempList
                        onComplete()
                    }
                }
            }
    }

    //TODO Error handling. Find another way that uses one snapshot listener?
    fun addActiveChatsSnapshotListener(currentUser: FirebaseUser) {
        addActiveChatsReferencesSnapshotListener(currentUser) {
            val tempList: MutableList<Chat> = mutableListOf()
            for (reference in activeConversationsReferences) {
                db.collection(CHATS).document(reference.id).get().addOnSuccessListener { snapshot ->
                        val conversation = snapshot.toObject<Chat>()
                        conversation?.let {
                            tempList.add(conversation)
                        }
                    }
            }
            activeChats.value = tempList
        }
    }


    private fun addAllUsersSnapshotListener() {
        db.collection(USERS).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.i("Fetching users error:", error.message.toString())
            } else {
                snapshot?.let {
                    val tempList = allUsers.value?.toMutableList()
                    for (document in snapshot) {
                        val user = document.toObject<User>()
                        tempList?.add(user)
                        allUsers.value = tempList
                    }
                }
            }
        }
    }
}