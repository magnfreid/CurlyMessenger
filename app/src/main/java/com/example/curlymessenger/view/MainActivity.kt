package com.example.curlymessenger.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curlymessenger.adapter.MyAdapter
import com.example.curlymessenger.databinding.ActivityMainBinding
import com.example.curlymessenger.modell.User
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private val userList = mutableListOf<User>()
    private val filteredUserList = mutableListOf<User>()
   // private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase database reference
       // database = FirebaseDatabase.getInstance().getReference("users")

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch data from Firebase
       // fetchUsersFromFirebase()
        // Set up search functionality
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        binding.myrcycler.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with onItemClick listener
        adapter = MyAdapter(userList) { user ->
            // Handle item click
           // openChatActivity(user)
        }
        binding.myrcycler.adapter = adapter
    }

   /* private fun fetchUsersFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (data in snapshot.children) {
                    val user = data.getValue(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                 filteredUserList.clear()
                filteredUserList.addAll(userList)
                adapter.notifyDataSetChanged()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showError(error.message)
            }

        })
    }*/


// fun search
    private fun setupSearchListener() {
        val searchEditText: EditText = binding.Editcharch
        searchEditText.addTextChangedListener { text ->
            val query = text.toString().lowercase().trim()
            filterUsers(query)
        }
    }

    private fun filterUsers(query: String) {
        filteredUserList.clear()
        if (query.isEmpty()) {
            filteredUserList.addAll(userList)
        } else {
            for (user in userList) {
                if (user.name.lowercase().contains(query)) {
                    filteredUserList.add(user)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun showError(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}
