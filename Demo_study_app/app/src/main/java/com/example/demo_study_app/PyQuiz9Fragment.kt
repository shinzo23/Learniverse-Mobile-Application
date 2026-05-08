package com.example.demo_study_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class PyQuiz9Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What is a database, and why is it used in applications?", listOf("A) A database is a collection of data, used for storing and retrieving information", "B) A database is a programming language, used for writing code", "C) A database is a file format, used for reading and writing files", "D) A database is a loop, used for iterating over data"), 0),
        QuizQuestion("What is SQLite, and how is it different from other databases?", listOf("A) SQLite is a lightweight, file-based database, while others are server-based", "B) SQLite is a server-based database, while others are file-based", "C) SQLite is a programming language, while others are databases", "D) SQLite is a file format, while others are databases"), 0),
        QuizQuestion("How do you connect to an SQLite database in Python?", listOf("A) Using the sqlite3.connect() function", "B) Using the sqlite3.open() function", "C) Using the sqlite3.read() function", "D) Using the sqlite3.write() function"), 0),
        QuizQuestion("What are CRUD operations, and how are they implemented in Python?", listOf("A) CRUD stands for Create, Read, Update, Delete, and they are implemented using SQL queries", "B) CRUD stands for Create, Read, Update, Delete, and they are implemented using loops", "C) CRUD stands for Create, Read, Update, Delete, and they are implemented using conditions", "D) CRUD stands for Create, Read, Update, Delete, and they are implemented using functions"), 0),
        QuizQuestion("How do you create a table in an SQLite database using Python?", listOf("A) Using the CREATE TABLE SQL statement", "B) Using the INSERT INTO SQL statement", "C) Using the SELECT SQL statement", "D) Using the DELETE SQL statement"), 0),
        QuizQuestion("What is the purpose of the cursor object in Python database programming?", listOf("A) To execute SQL queries and fetch results", "B) To connect to the database", "C) To close the database connection", "D) To create a new database"), 0),
        QuizQuestion("How do you handle database exceptions in Python?", listOf("A) Using try-except blocks", "B) Using if-else statements", "C) Using for loops", "D) Using while loops"), 0)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz9, container, false)

        questionText = view.findViewById(R.id.questionText)
        optionsGroup = view.findViewById(R.id.optionsGroup)
        submitButton = view.findViewById(R.id.submitButton)

        loadQuestion()

        submitButton.setOnClickListener {
            checkAnswer()
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                loadQuestion()
            } else {
                (activity as PythonActivity).completeIndexOrQuiz()
                Toast.makeText(requireContext(), "Quiz 9 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex10Fragment()) // Navigate to Index 2
            }
        }

        return view
    }

    private fun loadQuestion() {
        val question = questions[currentQuestionIndex]
        questionText.text = question.questionText
        optionsGroup.clearCheck()
        for (i in 0 until optionsGroup.childCount) {
            (optionsGroup.getChildAt(i) as RadioButton).text = question.options[i]
        }
    }

    private fun checkAnswer() {
        val selectedOptionId = optionsGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val selectedOption = view?.findViewById<RadioButton>(selectedOptionId)
            val selectedAnswerIndex = optionsGroup.indexOfChild(selectedOption)
            if (selectedAnswerIndex == questions[currentQuestionIndex].correctAnswerIndex) {
                score++
            }
        }
    }
}