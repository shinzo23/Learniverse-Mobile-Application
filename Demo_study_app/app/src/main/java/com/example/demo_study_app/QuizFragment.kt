package com.example.demo_study_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class QuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        val btnPython: Button = view.findViewById(R.id.btnPython)
        val btnJava: Button = view.findViewById(R.id.btnJava)
        val btnHtml: Button = view.findViewById(R.id.btnHtml)
        val btnCss: Button = view.findViewById(R.id.btnCss)
        val btnJs: Button = view.findViewById(R.id.btnJs)

        btnPython.setOnClickListener {
            startActivity(Intent(requireContext(), MasterQuiz::class.java))

        }

        btnJava.setOnClickListener { showToast("Starting Java Quiz") }
        btnHtml.setOnClickListener { showToast("Starting HTML Quiz") }
        btnCss.setOnClickListener { showToast("Starting CSS Quiz") }
        btnJs.setOnClickListener { showToast("Starting JavaScript Quiz") }

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
