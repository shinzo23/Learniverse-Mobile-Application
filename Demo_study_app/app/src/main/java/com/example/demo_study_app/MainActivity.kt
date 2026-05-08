package com.example.demo_study_app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the HomeFragment by default
        loadFragment(HomeFragment())

        // Set up Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_quiz -> {
                    loadFragment(QuizFragment())
                    true
                }
                R.id.nav_chatbot -> {
                    loadFragment(ChatbotFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // Handle Back Press
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                when (fragment) {
                    is HomeFragment -> {
                        // If the current fragment is HomeFragment, exit the app
                        finish()
                    }
                    else -> {
                        // Navigate back to the HomeFragment
                        loadFragment(HomeFragment())
                        // Update the selected item in the Bottom Navigation Bar
                        bottomNav.selectedItemId = R.id.nav_home
                    }
                }
            }
        }

        // Add the callback to the OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Add the transaction to the back stack
            .commit()
    }
}