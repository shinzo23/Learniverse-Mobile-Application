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

class PyQuiz8Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("How do you open and close a file in Python?", listOf("A) Using open() and close()", "B) Using read() and write()", "C) Using file() and close()", "D) Using open() and exit()"), 0),
        QuizQuestion("What is the difference between reading a file in text mode and binary mode?", listOf("A) Text mode reads the file as a string, and binary mode reads it as bytes", "B) Text mode reads the file as bytes, and binary mode reads it as a string", "C) Both modes read the file as a string", "D) Both modes read the file as bytes"), 0),
        QuizQuestion("How do you read a file line by line in Python?", listOf("A) Using a for loop", "B) Using readline()", "C) Using readlines()", "D) All of the above"), 3),
        QuizQuestion("What is the purpose of the with statement when working with files?", listOf("A) To automatically close the file after the block of code is executed", "B) To open the file in binary mode", "C) To read the file line by line", "D) To write data to the file"), 0),
        QuizQuestion("How do you write data to a CSV file in Python?", listOf("A) Using the csv.writer() function", "B) Using the csv.reader() function", "C) Using the csv.open() function", "D) Using the csv.close() function"), 0),
        QuizQuestion("What is JSON, and how do you parse JSON data in Python?", listOf("A) JSON is a data format, and it is parsed using the json module", "B) JSON is a file format, and it is parsed using the csv module", "C) JSON is a database, and it is parsed using the sqlite3 module", "D) JSON is a programming language, and it is parsed using the python module"), 0),
        QuizQuestion("How do you handle file exceptions, such as FileNotFoundError?", listOf("A) Using try-except blocks", "B) Using if-else statements", "C) Using for loops", "D) Using while loops"), 0)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz8, container, false)

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
                Toast.makeText(requireContext(), "Quiz 8 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex9Fragment()) // Navigate to Index 2
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