package com.example.demo_study_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_study_app.adapter.ChatAdapter
import com.example.demo_study_app.model.ChatMessage
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ChatbotFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter
    private val client = OkHttpClient()


    private val apiKey = "AIzaSyBkVxu5W0SXbrOA913o2GYS8kKdlanOlBA"
    private val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chatbot, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        messageEditText = view.findViewById(R.id.message_edit_text)
        sendButton = view.findViewById(R.id.send_btn)

        chatAdapter = ChatAdapter(chatMessages)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = chatAdapter

        sendButton.setOnClickListener {
            val userInput = messageEditText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                sendMessage(userInput)
                messageEditText.text.clear()
            }
        }

        return view
    }

    private fun sendMessage(message: String) {
        chatMessages.add(ChatMessage(message, isUser = true))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        recyclerView.scrollToPosition(chatMessages.size - 1)

        getBotResponse(message)
    }

    private fun getBotResponse(userMessage: String) {
        val mediaType = "application/json".toMediaType()

        // 🔹 Google Gemini API request format
        val requestBody = """
            {
                "contents": [
                    {
                        "parts": [
                            {
                                "text": "$userMessage"
                            }
                        ]
                    }
                ]
            }
        """.trimIndent().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ChatBot", "API Request Failed: ${e.message}", e)
                requireActivity().runOnUiThread {
                    chatMessages.add(ChatMessage("Sorry, we could not process your request. Try again", isUser = false))
                    chatAdapter.notifyItemInserted(chatMessages.size - 1)
                    recyclerView.scrollToPosition(chatMessages.size - 1)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                if (!response.isSuccessful) {
                    Log.e("ChatBot", "Response Failed: ${response.code} - $responseBody")
                    requireActivity().runOnUiThread {
                        chatMessages.add(ChatMessage("⚠️ Server Error: ${response.code}. Try again later.", isUser = false))
                        chatAdapter.notifyItemInserted(chatMessages.size - 1)
                        recyclerView.scrollToPosition(chatMessages.size - 1)
                    }
                    return
                }

                Log.d("ChatBot", "Full API Response: $responseBody")

                try {
                    if (responseBody != null) {
                        val jsonObject = JSONObject(responseBody)
                        val candidates = jsonObject.getJSONArray("candidates")

                        if (candidates.length() > 0) {
                            val botResponse = candidates.getJSONObject(0)
                                .getJSONObject("content")
                                .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text")

                            requireActivity().runOnUiThread {
                                chatMessages.add(ChatMessage(botResponse.trim(), isUser = false))
                                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                                recyclerView.scrollToPosition(chatMessages.size - 1)
                            }
                        } else {
                            Log.e("ChatBot", "Empty response from AI")
                            requireActivity().runOnUiThread {
                                chatMessages.add(ChatMessage("⚠️ AI returned an empty response.", isUser = false))
                                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                                recyclerView.scrollToPosition(chatMessages.size - 1)
                            }
                        }
                    } else {
                        Log.e("ChatBot", "Empty Response Body")
                        requireActivity().runOnUiThread {
                            chatMessages.add(ChatMessage("⚠️ No response from server.", isUser = false))
                            chatAdapter.notifyItemInserted(chatMessages.size - 1)
                            recyclerView.scrollToPosition(chatMessages.size - 1)
                        }
                    }
                } catch (e: JSONException) {
                    Log.e("ChatBot", "JSON Parsing Error: ${e.message}", e)
                    requireActivity().runOnUiThread {
                        chatMessages.add(ChatMessage("⚠️ Invalid response format. Try again.", isUser = false))
                        chatAdapter.notifyItemInserted(chatMessages.size - 1)
                        recyclerView.scrollToPosition(chatMessages.size - 1)
                    }
                }
            }
        })
    }
}