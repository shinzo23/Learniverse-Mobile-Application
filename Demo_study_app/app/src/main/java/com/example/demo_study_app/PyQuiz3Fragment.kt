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

class PyQuiz3Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What are the different types of operators in Python?", listOf("A) Arithmetic, logical, comparison", "B) Arithmetic, logical, comparison, assignment", "C) Arithmetic, logical, comparison, assignment, identity, membership", "D) Arithmetic, logical, comparison, assignment, identity, membership, bitwise"), 3),
        QuizQuestion("Explain the difference between `==` and `=` in Python.", listOf("A) `==` is used for assignment, and `=` is used for comparison", "B) `==` is used for comparison, and `=` is used for assignment", "C) Both are used for comparison", "D) Both are used for assignment"), 1),
        QuizQuestion("What is operator precedence, and how does it affect expressions?", listOf("A) It defines the order in which operators are evaluated", "B) It defines the data type of the result", "C) It defines the memory usage of the expression", "D) It defines the speed of the expression"), 0),
        QuizQuestion("How do logical operators (`and`, `or`, `not`) work in Python?", listOf("A) They perform arithmetic operations", "B) They combine or invert boolean values", "C) They compare two values", "D) They assign values to variables"), 1),
        QuizQuestion("What is the purpose of the `in` operator in Python?", listOf("A) To check if a value exists in a sequence", "B) To perform arithmetic operations", "C) To assign values to variables", "D) To compare two values"), 0),
        QuizQuestion("Write an expression to check if a number is even or odd.", listOf("A) `num % 2 == 0`", "B) `num / 2 == 0`", "C) `num * 2 == 0`", "D) `num - 2 == 0`"), 0),
        QuizQuestion("What is the difference between `is` and `==` in Python?", listOf("A) `is` checks for value equality, and `==` checks for identity", "B) `is` checks for identity, and `==` checks for value equality", "C) Both check for value equality", "D) Both check for identity"), 1)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz3, container, false)

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
                Toast.makeText(requireContext(), "Quiz 3 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex4Fragment()) // Navigate to Index 2
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