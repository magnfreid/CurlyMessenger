package com.example.curlymessenger.model

data class Chat(val id: String, val participants: List<User>, val messages: List<Message>? = null)




