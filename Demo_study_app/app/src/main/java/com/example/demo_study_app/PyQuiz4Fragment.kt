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

class PyQuiz4Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What is the purpose of `if-elif-else` statements in Python?", listOf("A) To perform arithmetic operations", "B) To execute code based on conditions", "C) To define functions", "D) To create loops"), 1),
        QuizQuestion("How does a `for` loop differ from a `while` loop?", listOf("A) A `for` loop is used for indefinite iteration, and a `while` loop is used for definite iteration", "B) A `for` loop is used for definite iteration, and a `while` loop is used for indefinite iteration", "C) Both are used for indefinite iteration", "D) Both are used for definite iteration"), 1),
        QuizQuestion("What is the purpose of the `break` statement in loops?", listOf("A) To skip the current iteration", "B) To exit the loop entirely", "C) To restart the loop", "D) To continue to the next iteration"), 1),
        QuizQuestion("How does the `continue` statement work in a loop?", listOf("A) It skips the current iteration and continues to the next one", "B) It exits the loop entirely", "C) It restarts the loop", "D) It pauses the loop"), 0),
        QuizQuestion("Write a Python program to print numbers from 1 to 10 using a `while` loop.", listOf("A) `i = 1 while i <= 10: print(i) i += 1`", "B) `for i in range(1, 11): print(i)`", "C) `i = 1 while i < 10: print(i)`", "D) `i = 0 while i <= 10: print(i)`"), 0),
        QuizQuestion("What is the purpose of the `pass` statement in Python?", listOf("A) To skip the current iteration", "B) To exit the loop entirely", "C) To act as a placeholder for future code", "D) To pause the loop"), 2),
        QuizQuestion("How do you handle multiple conditions in a single `if` statement?", listOf("A) Using `and`, `or`, and `not` operators", "B) Using `+`, `-`, and `*` operators", "C) Using `if-elif-else` statements", "D) Using `for` loops"), 0)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz4, container, false)

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
                Toast.makeText(requireContext(), "Quiz 4 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex5Fragment()) // Navigate to Index 2
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