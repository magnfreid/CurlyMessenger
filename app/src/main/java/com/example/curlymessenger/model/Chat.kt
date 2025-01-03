package com.example.curlymessenger.model

data class Chat(
    val id: String = "",
    val participantsById: List<String> = listOf(),
    val participants: List<User> = listOf(),
    val messages: List<Message>? = null
)




