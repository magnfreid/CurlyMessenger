package com.example.curlymessenger.view


import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.curlymessenger.R
import com.example.curlymessenger.databinding.ActivityMainBinding
import com.example.curlymessenger.viewmodel.ChatViewModel
import com.example.curlymessenger.viewmodel.UserViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setContentView(binding.root)
        enableEdgeToEdge()

        // Test users
        // chatViewModel.startNewChat(listOf( User("08NyniWLyMdu55RqystO9A4P2En1"), User("MeWTWrqEGTaeLafnqZlIyWuXVHe2")), {}, {})

        userViewModel.currentUser.observe(this) {
            Log.i("Current user: ", it.toString())
        }

        chatViewModel.activeChats.observe(this) {
            Log.i("Active Chats: ", it.toString())
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UserFragment())
                .commit()
        }
    }
}