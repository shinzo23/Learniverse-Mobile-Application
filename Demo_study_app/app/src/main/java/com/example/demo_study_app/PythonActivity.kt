package com.example.demo_study_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class PythonActivity : AppCompatActivity(),
    PyIndex1Fragment.OnIndexCompletedListener,
    PyIndex2Fragment.OnIndexCompletedListener,
    PyIndex3Fragment.OnIndexCompletedListener,
    PyIndex4Fragment.OnIndexCompletedListener,
    PyIndex5Fragment.OnIndexCompletedListener,
    PyIndex6Fragment.OnIndexCompletedListener,
    PyIndex7Fragment.OnIndexCompletedListener,
    PyIndex8Fragment.OnIndexCompletedListener,
    PyIndex9Fragment.OnIndexCompletedListener,
    PyIndex10Fragment.OnIndexCompletedListener,
    PyIndex11Fragment.OnIndexCompletedListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private val totalIndexes = 11 // Total number of indexes and quizzes
    private var completedIndexes = 0 // Number of completed indexes/quizzes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_python)

//         Initialize progress bar and progress text
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        progressBar.max = 100 // Each index + quiz = 100%

        // Load saved progress
        loadProgress()

        // Load the first fragment by default
        loadFragment(PyIndex1Fragment())

        // Handle the back button using OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@PythonActivity, PythonIndexActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun completeIndexOrQuiz() {
        if (completedIndexes < totalIndexes) { // Prevent exceeding total indexes
            completedIndexes++
            saveProgress()
            updateProgressBar()
            Toast.makeText(this, "Progress: $completedIndexes/$totalIndexes", Toast.LENGTH_SHORT).show()

            if (completedIndexes >= totalIndexes) {
                Toast.makeText(this, "Course Completed! 100%", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProgress() {
        val sharedPreferences = getSharedPreferences("CourseProgress", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("completedIndexes", completedIndexes)
        editor.apply()
    }

    private fun loadProgress() {
        val sharedPreferences = getSharedPreferences("CourseProgress", Context.MODE_PRIVATE)
        completedIndexes = sharedPreferences.getInt("completedIndexes", 0)
        updateProgressBar()
    }

    private fun updateProgressBar() {
        val progress = (completedIndexes.toFloat() / totalIndexes) * 100
        progressBar.progress = progress.toInt()
        progressText.text = getString(R.string.progress_percentage, progress.toInt())

        // Debug log to track values
        Log.d("PythonActivity", "Completed Indexes: $completedIndexes, Progress: ${progressBar.progress}")
    }

    // ✅ Implementing the required method from OnIndexCompletedListener
    override fun onIndexCompleted() {
        completeIndexOrQuiz()
    }
}
