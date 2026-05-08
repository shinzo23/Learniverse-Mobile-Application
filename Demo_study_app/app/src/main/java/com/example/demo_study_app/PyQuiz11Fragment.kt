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

class PyQuiz11Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What is a dictionary in Python?", listOf("A data type", "A function", "A loop", "A module"), 0),
        QuizQuestion("What does the `keys()` method return?", listOf("List of keys", "List of values", "List of items", "List of pairs"), 0)
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz11, container, false)

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
                // Quiz completed, show the score and display the CongratsDialog
                Toast.makeText(requireContext(), "Quiz 11 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()

                // Show the CongratsDialog
                val congratsDialog = CongratsDialog()
                congratsDialog.show(parentFragmentManager, "CongratsDialog")
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