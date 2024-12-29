package com.example.curlymessenger.model

data class Chat(val id: String, val participants: List<User>, val messages: List<Message> = listOf())

data class Message(val id: String, val messageText: String, val sender: User)

data class ChatReference(val id:String, val participants: List<User>)

