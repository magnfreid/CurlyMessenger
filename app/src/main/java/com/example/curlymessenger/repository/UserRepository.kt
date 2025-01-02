package com.example.curlymessenger.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.curlymessenger.model.PATH_USERS
import com.example.curlymessenger.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class UserRepository {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    init {
        addUsersSnapshotListener()
        addCurrentUserSnapshotListener()
    }

    /**
     * All registered users of the app.
     */
    var users: MutableLiveData<List<User>> = MutableLiveData(listOf())
        private set

    /**
     * The currently logged in user.
     */
    var currentUser: MutableLiveData<User?> = MutableLiveData(null)
        private set

    /**
     * Signs in a user to Firebase Auth.
     */
    fun signInWithEmailAndPassword(
        email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    /**
     * Registers a new user with Firebase Auth and adds a new user to the Cloud Firestore database.
     */
    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        nickname: String,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
            val firebaseUser = authResult.user
            firebaseUser?.let {
                val newUser = User(firebaseUser.uid, nickname, firebaseUser.email)
                addUserToDatabase(newUser)
                onSuccess(authResult)
            }
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    private fun addUserToDatabase(user: User) {
        db.collection(PATH_USERS).document(user.id).set(user)
    }

    private fun addUsersSnapshotListener() {
        db.collection(PATH_USERS).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.i("Fetching users error:", error.message.toString())
            } else {
                snapshot?.let {
                    val tempList = it.toObjects<User>()
                    users.value = tempList
                }
            }
        }
    }

    private fun addCurrentUserSnapshotListener() {
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            firebaseUser?.let {
                db.collection(PATH_USERS).document(firebaseUser.uid).get()
                    .addOnSuccessListener { snapshot ->
                        currentUser.value = snapshot.toObject<User>()
                    }.addOnFailureListener {
                        currentUser.value = null
                    }
            }
        }
    }
}