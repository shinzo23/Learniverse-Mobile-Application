package com.example.demo_study_app

import Index
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PythonIndexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_python_index)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.indexRecyclerView)
        val pyStartNowButton = findViewById<Button>(R.id.startNowButton)

        pyStartNowButton.setOnClickListener {
            // Code to run when the Start Now button is clicked
            val intent = Intent(this, PythonActivity::class.java)
            startActivity(intent)
        }

        // Set LayoutManager for RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val indexList = listOf(
            Index("●  Introduction to Python Programming", listOf(
                "○ Overview of Python: Features and Real-World Applications",
                "○ Understanding Python’s Popularity and Versatility",
                "○ Setting Up the Development Environment",
                "○ Writing and Executing Your First Python Script"
            )),
            Index("●  Fundamental Concepts: Variables and Data Types", listOf(
                "○ Understanding Variables: Declaration and Scope",
                "○ Data Types in Python: Numbers, Strings, Booleans, and Collections",
                "○ Type Conversion and Best Practices",
                "○ Implementing Data Storage Strategies"
            )),
            Index("●  Operators and Expressions", listOf(
                "○ Arithmetic, Logical, and Comparison Operators",
                "○ Assignment, Identity, and Membership Operators",
                "○ Operator Precedence and Associativity",
                "○ Hands-on Exercise: Developing Basic Expressions"
            )),
            Index("●  Control Structures: Conditional Statements and Loops", listOf(
                "○ Implementing Conditional Logic with If-Elif-Else",
                "○ Iterative Execution: For and While Loops",
                "○ Advanced Loop Control: Break, Continue, and Pass",
                "○ Practical Application: Developing an Interactive Guessing Game"
            )),
            Index("●  Functions and Modular Programming", listOf(
                "○ Defining and Calling Functions",
                "○ Function Parameters, Arguments, and Return Values",
                "○ Understanding Scope and Lifetime of Variables",
                "○ Hands-on Project: Implementing a Modular Calculator"
            )),
            Index("●  Exception Handling and Debugging", listOf(
                "○ Introduction to Error Types in Python",
                "○ Implementing Exception Handling with Try-Except Blocks",
                "○ Debugging Techniques and Best Practices",
                "○ Challenge: Identifying and Resolving Common Python Errors"
            )),
            Index("●  Object-Oriented Programming (OOP) in Python", listOf(
                "○ Understanding Object-Oriented Principles",
                "○ Creating and Utilizing Classes and Objects",
                "○ Implementing Inheritance and Polymorphism",
                "○ Mini Project: Developing a Basic Banking System"
            )),
            Index("●  File Handling: Working with Data Files", listOf(
                "○ Reading and Writing Text Files",
                "○ Handling Structured Data with CSV Files",
                "○ Managing JSON Data for Application Development",
                "○ Practical Implementation: Building a To-Do List Application"
            )),
            Index("●  Introduction to Databases with Python", listOf(
                "○ Understanding Database Management Systems",
                "○ Introduction to SQLite and Its Integration with Python",
                "○ Performing CRUD Operations: Create, Read, Update, Delete",
                "○ Mini Project: Developing a Note-Taking Application"
            )),
            Index("●  GUI Development: Creating Interactive Applications", listOf(
                "○ Fundamentals of Graphical User Interfaces (GUI)",
                "○ Designing Interfaces with Tkinter",
                "○ Handling User Input and Events",
                "○ Mini Project: Building an Interactive Calculator"
            )),
            Index("●  Final Capstone Project: Build and Showcase Your Python Masterpiece", listOf(
                "○ Creating a Python Application",
            ))
        )




        // Set Adapter with the expandable functionality
        recyclerView.adapter = ExpandableIndexAdapter(indexList)
    }
}
