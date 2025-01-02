package com.example.curlymessenger.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curlymessenger.R
import com.example.curlymessenger.model.User
import com.squareup.picasso.Picasso

class UsersAdapter(val context: Context, val users: List<User>,
                   private val onUserClicked: (User) -> Unit) :
                    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

     val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView = layoutInflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        Picasso.get() // Ladda bilden med Picasso
            .load(user.avatar) // URL till bilden
            .placeholder(R.drawable.avatar) // Reservbild
            .error(R.drawable.avatar) // Bild vid fel
            .into(holder.imgAvatar)

        holder.tv_nickName.text = user.nickname

        holder.itemView.setOnClickListener {
            onUserClicked(user)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar = itemView.findViewById<ImageView>(R.id.imgAvatar)
        val tv_nickName = itemView.findViewById<TextView>(R.id.tv_nickName)
    }
}