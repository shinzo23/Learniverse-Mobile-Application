package com.example.demo_study_app

import android.content.Intent
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

class PyQuiz1Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What are the key features of Python that make it popular for beginners and professionals?", listOf("A) Complex syntax", "B) Readability and simplicity", "C) Limited libraries", "D) Slow execution"), 1),
        QuizQuestion("Name three real-world applications of Python.", listOf("A) Web development, gaming, and space exploration", "B) Only gaming", "C) Only web development", "D) Only space exploration"), 0),
        QuizQuestion("What is the purpose of setting up a development environment for Python?", listOf("A) To make Python harder to use", "B) To provide tools for writing, testing, and debugging code", "C) To slow down the development process", "D) To limit the functionality of Python"), 1),
        QuizQuestion("How do you write and execute a Python script?", listOf("A) Write code in a `.java` file and run it with Python", "B) Write code in a `.py` file and run it with the Python interpreter", "C) Write code in a `.txt` file and run it with Python", "D) Write code in a `.html` file and run it with Python"), 1),
        QuizQuestion("What is the difference between Python 2 and Python 3?", listOf("A) Python 2 is faster than Python 3", "B) Python 3 has better syntax and more features", "C) Python 2 is more modern than Python 3", "D) There is no difference"), 1),
        QuizQuestion("What is the role of the Python interpreter?", listOf("A) To compile Python code into machine code", "B) To execute Python code line by line", "C) To debug Python code automatically", "D) To write Python code"), 1),
        QuizQuestion("What is the significance of indentation in Python?", listOf("A) It is optional and used for readability", "B) It defines the structure and scope of the code", "C) It is used to comment out code", "D) It is used to declare variables"), 1)
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz1, container, false)

        questionText = view.findViewById(R.id.questionText)
        optionsGroup = view.findViewById(R.id.optionsGroup)
        submitButton = view.findViewById(R.id.submitButton)

        loadQuestion()

        submitButton.setOnClickListener {
            val selectedOptionId = optionsGroup.checkedRadioButtonId

            if (selectedOptionId == -1) {
                // No option selected, show a warning message
                Toast.makeText(requireContext(), "Please select an answer before proceeding!", Toast.LENGTH_SHORT).show()
            } else {
                // Option selected, proceed with checking the answer
                checkAnswer()

                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    loadQuestion()
                } else {
                    if (score >= 5) {
                        (activity as PythonActivity).completeIndexOrQuiz()
                        Toast.makeText(requireContext(), "Quiz 1 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                        (activity as PythonActivity).loadFragment(PyIndex2Fragment()) // Navigate to Index 2
                    } else {
                        Toast.makeText(requireContext(), "You need at least 5 correct answers to proceed. Your score: $score/${questions.size}", Toast.LENGTH_LONG).show()
                        resetQuiz()
                        (activity as PythonActivity).loadFragment(PyIndex1Fragment())
                        Toast.makeText(requireContext(), "Learn and Attempt Quiz Again", Toast.LENGTH_LONG).show()
                  }

                }
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

        if (currentQuestionIndex == questions.size - 1) {

            submitButton.text = "Submit"
        } else {
            submitButton.text = "Next Question"
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

    private fun resetQuiz() {
        currentQuestionIndex = 0
        score = 0
    }
}

