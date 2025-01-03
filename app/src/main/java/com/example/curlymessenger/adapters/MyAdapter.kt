package com.example.curlymessenger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curlymessenger.databinding.ItemcontactBinding
import com.example.curlymessenger.model.Chat


class MyAdapter(private val chats: List<Chat>, private val onItemClick: (Chat) -> Unit) :
    RecyclerView.Adapter<MyAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(private val binding: ItemcontactBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvUsername = binding.textusername
        val tvMessage = binding.textmessege

        init {
            binding.root.setOnClickListener {
                val chat = chats[adapterPosition]
                onItemClick(chat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemcontactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chats[position]

        // Assuming the chat has a list of messages and we want to display the latest message
        val lastMessage = chat.messages?.lastOrNull()

        holder.tvUsername.text = chat.participants.firstOrNull()?.nickname ?: "Unknown"
        holder.tvMessage.text = lastMessage?.messageText ?: "No message"
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}