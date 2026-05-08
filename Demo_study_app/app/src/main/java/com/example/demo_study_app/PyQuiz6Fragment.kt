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

class PyQuiz6Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What is an exception in Python, and how is it different from a syntax error?",
            listOf("A) An exception is a runtime error, while a syntax error occurs during parsing",
                "B) An exception is a syntax error, while a runtime error occurs during execution",
                "C) An exception and a syntax error are the same",
                "D) An exception is a logical error, while a syntax error is a runtime error"),
            0),

        QuizQuestion("How do you handle exceptions using try-except blocks?",
            listOf("A) By placing the code that might raise an exception in the try block and handling it in the except block",
                "B) By placing the code that might raise an exception in the except block and handling it in the try block",
                "C) By using if-else statements",
                "D) By using for loops"),
            0),

        QuizQuestion("What is the purpose of the finally block in exception handling?",
            listOf("A) To execute code regardless of whether an exception occurs",
                "B) To handle exceptions",
                "C) To skip the exception",
                "D) To raise an exception"),
            0),

        QuizQuestion("How do you raise a custom exception in Python?",
            listOf("A) Using raise",
                "B) Using throw",
                "C) Using catch",
                "D) Using finally"),
            0),

        QuizQuestion("What is the difference between assert and raise in Python?",
            listOf("A) assert is used for debugging, and raise is used to trigger exceptions",
                "B) assert is used to trigger exceptions, and raise is used for debugging",
                "C) Both are used for debugging",
                "D) Both are used to trigger exceptions"),
            0),

        QuizQuestion("How do you debug a Python program using print statements?",
            listOf("A) By adding print() statements to display variable values and program flow",
                "B) By using try-except blocks",
                "C) By using assert statements",
                "D) By using raise statements"),
            0),

        QuizQuestion("What is the purpose of the logging module in Python?",
            listOf("A) To log messages for debugging and tracking program execution",
                "B) To handle exceptions",
                "C) To raise exceptions",
                "D) To skip exceptions"),
            0)
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz6, container, false)

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
                Toast.makeText(requireContext(), "Quiz 6 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex7Fragment()) // Navigate to Index 2
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