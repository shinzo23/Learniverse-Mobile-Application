package com.example.demo_study_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_study_app.R
import com.example.demo_study_app.model.ChatMessage

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userMessage: TextView = itemView.findViewById(R.id.user_message)
        private val botMessage: TextView = itemView.findViewById(R.id.bot_message)

        fun bind(message: ChatMessage) {
            if (message.isUser) {
                userMessage.visibility = View.VISIBLE
                botMessage.visibility = View.GONE
                userMessage.text = message.message
            } else {
                botMessage.visibility = View.VISIBLE
                userMessage.visibility = View.GONE
                botMessage.text = message.message
            }
        }
    }
}