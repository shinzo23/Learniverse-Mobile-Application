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

class PyQuiz10Fragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        QuizQuestion("What is a Graphical User Interface (GUI), and why is it important?", listOf("A) A GUI allows users to interact with an application using visual elements", "B) A GUI is a programming language, used for writing code", "C) A GUI is a file format, used for reading and writing files", "D) A GUI is a loop, used for iterating over data"), 0),
        QuizQuestion("What is Tkinter, and how is it used in Python?", listOf("A) Tkinter is a library for creating GUIs in Python", "B) Tkinter is a database, used for storing data", "C) Tkinter is a file format, used for reading and writing files", "D) Tkinter is a loop, used for iterating over data"), 0),
        QuizQuestion("How do you create a window using Tkinter?", listOf("A) Using the Tk() class", "B) Using the Window() class", "C) Using the Frame() class", "D) Using the Label() class"), 0),
        QuizQuestion("What is the purpose of widgets in Tkinter, and name three commonly used widgets?", listOf("A) Widgets are visual elements, such as Button, Label, and Entry", "B) Widgets are loops, such as for, while, and if", "C) Widgets are conditions, such as if, elif, and else", "D) Widgets are functions, such as def, return, and lambda"), 0),
        QuizQuestion("How do you handle button clicks in a Tkinter application?", listOf("A) Using the command parameter in the Button widget", "B) Using the click() method", "C) Using the handle() method", "D) Using the event() method"), 0),
        QuizQuestion("What is the purpose of the pack(), grid(), and place() methods in Tkinter?", listOf("A) To arrange widgets in the window", "B) To create widgets", "C) To delete widgets", "D) To update widgets"), 0),
        QuizQuestion("How do you create a simple calculator using Tkinter?", listOf("A) By creating buttons for digits and operations, and using event handlers to perform calculations", "B) By using loops to perform calculations", "C) By using conditions to perform calculations", "D) By using functions to perform calculations"), 0)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_quiz10, container, false)

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
                Toast.makeText(requireContext(), "Quiz 10 Completed! Score: $score/${questions.size}", Toast.LENGTH_SHORT).show()
                (activity as PythonActivity).loadFragment(PyIndex11Fragment()) // Navigate to Index 2
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