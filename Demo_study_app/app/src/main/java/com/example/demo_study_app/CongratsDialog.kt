package com.example.demo_study_app

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CongratsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the custom layout
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_congrats_dialog, null)

        // Find the button in the layout
        val masterQuizButton = view.findViewById<Button>(R.id.masterQuizButton)

        // Set click listener for the "Attempt Master Quiz" button
        masterQuizButton.setOnClickListener {
            // Navigate to the MasterQuizActivity
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            dismiss() // Close the dialog
        }

        // Build the dialog
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false) // Prevent the user from dismissing the dialog by clicking outside
            .create()
    }
}