package com.example.curlymessenger.adapter

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curlymessenger.R
import com.example.curlymessenger.modell.User
//import de.hdodenhof.circleimageview.CircleImageView

class MyAdapter(
    private val userList: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<MyAdapter.UserViewHolder>() {

    // ViewHolder class
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textUsername: TextView = itemView.findViewById(R.id.textusername)
        private val textMessage: TextView = itemView.findViewById(R.id.textmessege)
            // private val imageUser: CircleImageView = itemView.findViewById(R.id.imgeUser)

        fun bind(user: User, onItemClick: (User) -> Unit) {
            textUsername.text = user.name
            textMessage.text = user.messge




            // Set click listener
            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcontact, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
