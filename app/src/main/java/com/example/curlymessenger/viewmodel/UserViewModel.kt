package com.example.curlymessenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curlymessenger.model.User
import com.example.curlymessenger.repository.UserRepository

class UserViewModel : ViewModel() {

    private val userRepo = UserRepository()

    val allUsers: LiveData<List<User>> get() = userRepo.users
    val currentUser: LiveData<User?> get() = userRepo.currentUser

}