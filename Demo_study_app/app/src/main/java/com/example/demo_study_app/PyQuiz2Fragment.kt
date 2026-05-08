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

class PyQuiz2Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What is a variable, and how is it declared in Python?", listOf("A) A variable is a fixed value, declared using `const`", "B) A variable is a container for storing data, declared using `=`", "C) A variable is a function, declared using `def`", "D) A variable is a loop, declared using `for`"), 1),
        QuizQuestion("List the basic data types in Python and provide an example of each.", listOf("A) `int`, `float`, `str`, `bool`", "B) `int`, `float`, `str`, `list`", "C) `int`, `float`, `str`, `dict`", "D) `int`, `float`, `str`, `tuple`"), 0),
        QuizQuestion("What is type conversion, and why is it important?", listOf("A) It converts data types automatically without user input", "B) It allows changing the data type of a variable explicitly", "C) It is used to delete variables", "D) It is used to create new variables"), 1),
        QuizQuestion("How do you check the data type of a variable in Python?", listOf("A) Using `type()`", "B) Using `check()`", "C) Using `typeof()`", "D) Using `data()`"), 0),
        QuizQuestion("What is the difference between mutable and immutable data types?", listOf("A) Mutable types can be changed after creation, while immutable types cannot", "B) Immutable types can be changed after creation, while mutable types cannot", "C) Both mutable and immutable types can be changed after creation", "D) Neither mutable nor immutable types can be changed after creation"), 0),
        QuizQuestion("How do you declare and use a constant in Python?", listOf("A) Using `const` keyword", "B) Using uppercase variable names by convention", "C) Using `final` keyword", "D) Using `static` keyword"), 1),
        QuizQuestion("What is the scope of a variable, and how does it affect your code?", listOf("A) Scope defines where a variable can be accessed", "B) Scope defines the data type of a variable", "C) Scope defines the value of a variable", "D) Scope defines the memory location of a variable"), 0)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz2, container, false)

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
                Toast.makeText(requireContext(), "Quiz 2 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex3Fragment()) // Navigate to Index 2
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