package com.example.curlymessenger.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.curlymessenger.adapter.MyAdapter
import com.example.curlymessenger.databinding.FragmentMainBinding
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.Message
import com.example.curlymessenger.model.User
import com.example.curlymessenger.viewmodel.ChatViewModel


class FragmentMain : Fragment() {


    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var chatViewModel: ChatViewModel
    private val chatList = mutableListOf<Chat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        // Set up RecyclerView
        setupRecyclerView()

        // Observe chats data
        observeChats()
    }

    private fun setupRecyclerView() {
        binding.myrcycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = MyAdapter(chatList) { chat ->
            Toast.makeText(requireContext(), "Clicked on chat: ${chat.id}", Toast.LENGTH_SHORT).show()
        }
        binding.myrcycler.adapter = adapter
    }

    private fun observeChats() {
        chatViewModel.activeChats.observe(viewLifecycleOwner) { chats ->
            chatList.clear()
            chatList.addAll(chats)

            if (chatList.isEmpty()) {
                binding.myrcycler.visibility = View.GONE
            } else {
                binding.myrcycler.visibility = View.VISIBLE
            }

            adapter.notifyDataSetChanged()
        }
    }


}