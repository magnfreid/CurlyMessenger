package com.example.curlymessenger.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curlymessenger.adapter.UserAdapter
import com.example.curlymessenger.databinding.FragmentUserBinding
import com.example.curlymessenger.viewmodel.UserViewModel

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupRecyclerView()
        searchUser()

        userViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter { user ->
            userViewModel.startNewChat(listOf(user), {
                Toast.makeText(requireContext(), "Ny chatt startad med ${user.nickname}", Toast.LENGTH_SHORT).show()
            }, { exception ->
                Toast.makeText(requireContext(), "Fel: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        }
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun searchUser() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterUsers(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterUsers(query: String) {
        val filteredList = userViewModel.allUsers.value?.filter { user ->
            user.nickname?.contains(query, ignoreCase = true) == true
        }
        adapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}