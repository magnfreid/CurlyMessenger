package com.example.curlymessenger.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.curlymessenger.adapter.MyAdapter
import com.example.curlymessenger.databinding.FragmentMainBinding
import com.example.curlymessenger.model.Chat
import com.example.curlymessenger.model.Message
import com.example.curlymessenger.model.User


class FragmentMain : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MyAdapter
    private val chatList = mutableListOf<Chat>()
    // private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Realtime Database reference
        // database = FirebaseDatabase.getInstance().getReference("chats")

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch data from Firebase
        //fetchChatsFromFirebase()

        //generatdatalocal prova
        generateLocalData()
    }
    // locadata prova recyclerview
    private fun generateLocalData() {

        val user1 = User(id = "1", nickname = "Alice", email = "alice@example.com")
        val user2 = User(id = "2", nickname = "Bob", email = "bob@example.com")
        val message1 = Message(id = "1", messageText = "Hello!", sender = user1)
        val message2 = Message(id = "2", messageText = "Hi Alice!", sender = user2)
        val user3 = User(id = "1", nickname = "Alice", email = "alice@example.com")

        val chat = Chat(id = "1", participants = listOf(user1, user2), messages = listOf(message1, message2))
        chatList.add(chat)
        val chat1 = Chat(id = "1", participants = listOf(user1, user3), messages = listOf(message1, message2))
        chatList.add(chat1)
        val chat2 = Chat(id = "1", participants = listOf(user1, user3), messages = listOf(message1, message2))
        chatList.add(chat2)

    }

    private fun setupRecyclerView() {
        binding.myrcycler.layoutManager = LinearLayoutManager(requireContext())  // Use requireContext for Fragment
        adapter = MyAdapter(chatList) { chat ->
            //need fragment chatt to go till chatt
            Toast.makeText(requireContext(), "Clicked: ${chat.id}", Toast.LENGTH_SHORT).show()
        }
        binding.myrcycler.adapter = adapter
    }

    //data from firbase
    /*  private fun fetchChatsFromFirebase() {
         database.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(snapshot: DataSnapshot) {
                 chatList.clear()  // Clear the current data in the list
                 for (data in snapshot.children) {
                     val chat = data.getValue(Chat::class.java)
                     if (chat != null) {
                         chatList.add(chat)  // Add the chat data to the list
                     }
                 }
                 adapter.notifyDataSetChanged()  // Notify the adapter that data has changed
             }

             override fun onCancelled(error: DatabaseError) {
                 showError(error.message)  // Show error message if fetching data fails
             }
         })
     }
     */

    private fun showError(message: String) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
    }
}