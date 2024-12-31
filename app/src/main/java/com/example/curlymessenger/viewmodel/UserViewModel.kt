package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.User
import com.example.curlymessenger.repository.AuthRepository
import com.example.curlymessenger.repository.DatabaseRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {

    private val dbRepo = DatabaseRepository()
    private val authRepo = AuthRepository()

    val allUsers: LiveData<List<User>> get() = dbRepo.users
    val currentUser: LiveData<FirebaseUser> get() = authRepo.currentUser

}