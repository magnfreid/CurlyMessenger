package com.example.curlymessenger.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curlymessenger.adpters.UsersAdapter
import com.example.curlymessenger.databinding.FragmentUsersBinding
import com.example.curlymessenger.model.User

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UsersAdapter

    // Exempeldata
    private var userList = listOf(
        User("1","John Doe", "jonas@gmail.com","https://pixabay.com/vectors/avatar-boy-cartoon-comic-human-2027366/"),
        User("2", "Jane Smith", "jonas@gmail.com","https://pixabay.com/vectors/avatar-boy-cartoon-comic-human-2027366/"),
        User("3", "Alice Johnson","jonas@gmail.com","https://pixabay.com/vectors/avatar-boy-cartoon-comic-human-2027366/"),
    )

    private var filteredList = userList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ställ in RecyclerView
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersAdapter(requireContext(), filteredList) { user ->
            onUserClicked(user)
        }
        binding.rvUsers.adapter = adapter

        // Lägg till textlyssnare för sökfältet
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterUsers(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSearch.setOnClickListener {
            filterUsers(binding.etSearch.text.toString())
        }
    }

    private fun filterUsers(query: String) {
        filteredList = if (query.isEmpty()) {
            userList
        } else {
            userList.filter { user ->
                user.nickname?.contains(query, ignoreCase = true) == true
            }
        }
        adapter = UsersAdapter(requireContext(), filteredList) { user ->
            onUserClicked(user)
        }
        binding.rvUsers.adapter = adapter
    }

    private fun onUserClicked(user: User) {
        println("Användare klickad: ${user.nickname}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
