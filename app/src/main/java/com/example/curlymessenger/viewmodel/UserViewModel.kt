package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.User
import com.example.curlymessenger.repository.FirestoreRepository

class UserViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val _allUsers = MutableLiveData<List<User>?>()
    val allUsers: LiveData<List<User>?> get() = _allUsers

    init {
        // Hämta alla användare när viewmodelen initieras
        getAllUsers()
    }

    fun getAllUsers() {
        firestoreRepository.getAllUsers { users ->
            _allUsers.value = users
        }
    }

    fun searchUsers(query: String) {
        firestoreRepository.searchUsers(query) { users ->
            _allUsers.value = users
        }
    }
}
