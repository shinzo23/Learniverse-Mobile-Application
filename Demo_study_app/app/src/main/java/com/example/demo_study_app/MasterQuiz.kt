package com.example.demo_study_app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MasterQuiz : AppCompatActivity() {
    private lateinit var tvQuestion: TextView
    private lateinit var buttons: List<Button>
    private lateinit var btnNext: Button
    private var index = 0
    private var score = 0
    private var isAnswered = false

    private val questions = listOf(
        Question("What is the correct extension of a Python file?", ".python", ".py", ".pyt", ".pt", "B"),
        Question("How do you display output in Python?", "print()", "output()", "display()", "show()", "A"),
        Question("What will type(5) return?", "int", "float", "str", "list", "A"),
        Question("What keyword is used to define a function in Python?", "function", "def", "define", "fun", "B"),
        Question("What is the output of 3 * 3 ** 3?", "81", "27", "243", "9", "C"),
        Question("How do you take user input in Python?", "input()", "get()", "read()", "scan()", "A"),
        Question("What will bool([]) return?", "True", "False", "None", "Error", "B"),
        Question("Which of the following is a mutable datatype in Python?", "Tuple", "String", "List", "Set", "C"),
        Question("What is the output of len(\"Python\")?", "5", "6", "7", "8", "B"),
        Question("Which of the following is not a valid variable name in Python?", "my_var", "1variable", "_name", "var123", "B"),
        Question("What does continue do in a loop?", "Stops the loop", "Restarts the loop", "Skips the current iteration", "Exits the loop", "C"),
        Question("How do you open a file in Python for writing?", "open(\"file.txt\", \"r\")", "open(\"file.txt\", \"rb\")", "open(\"file.txt\", \"w\")", "open(\"file.txt\", \"wb\")", "C"),
        Question("What is the output of bool(\"False\")?", "True", "False", "None", "Error", "A"),
        Question("What will range(5) return?", "[0, 1, 2, 3, 4]", "[1, 2, 3, 4, 5]", "[0, 1, 2, 3, 4, 5]", "None", "A"),
        Question("Which module is used for handling JSON data in Python?", "os", "json", "csv", "sys", "B"),
        Question("What is lambda in Python?", "A variable", "A datatype", "An anonymous function", "A keyword", "C"),
        Question("What is the output of list(range(2, 10, 2))?", "[2, 4, 6, 8, 10]", "[2, 4, 6, 8]", "[2, 3, 4, 5, 6, 7, 8, 9]", "[2, 10, 2]", "B"),
        Question("What is the output of type([])?", "tuple", "list", "dict", "set", "B"),
        Question("How do you create an infinite loop in Python?", "for x in range () :", "while True :", "while x == x :", "loop()", "B"),
        Question("What is the correct way to start a class in Python?", "class MyClass() :", "def MyClass() :", "new MyClass() :", "create MyClass() :", "A"),
        Question("Which statement is used to exit a loop in Python?", "break", "continue", "exit", "stop", "A"),
        Question("What is the default return value of a function that does not explicitly return anything?", "0", "\"\" (empty string)", "None", "False", "C"),
        Question("What will 5 // 2 return?", "2.5", "2", "3", "2.0", "B"),
        Question("How can you add an element to a set?", "set.append(value)", "set.add(value)", "set.insert(value)", "set.put(value)", "B"),
        Question("What does is operator compare?", "Values of two variables", "Memory location of two objects", "Data types of two variables", "Length of two variables", "B"),
        Question("What will bool(0) return?", "True", "False", "None", "Error", "B"),
        Question("How do you create an empty dictionary in Python?", "{}", "[]", "dict()", "Both (A) and (B)", "D"),
        Question("Which of the following data structures does not allow duplicate values?", "List", "Tuple", "Dictionary", "Set", "D"),
        Question("What will 3 ** 2 return?", "9", "6", "8", "12", "A"),
        Question("Which operator is used for exponentiation in Python?", "^", "**", "//", "%%", "B")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_quiz)

        tvQuestion = findViewById(R.id.tvQuestion)
        buttons = listOf(
            findViewById(R.id.btnOptionA),
            findViewById(R.id.btnOptionB),
            findViewById(R.id.btnOptionC),
            findViewById(R.id.btnOptionD)
        )
        btnNext = findViewById(R.id.btnNext)

        btnNext.isEnabled = false
        btnNext.setOnClickListener { goToNextQuestion() }

        loadQuestion()
    }

    private fun loadQuestion() {
        if (index >= questions.size) {
            navigateToResult()
            return
        }

        val q = questions[index]

        tvQuestion.text = q.questionText
        isAnswered = false
        btnNext.isEnabled = false

        buttons.forEachIndexed { i, btn ->
            val option = ('A' + i).toString()
            btn.text = q.options[i]


            btn.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.LTGRAY)

            btn.isEnabled = true

            btn.setOnClickListener {
                if (!isAnswered) {
                    handleAnswer(btn, option, q.correctAnswer)
                }
            }
        }
    }


    private fun handleAnswer(selectedBtn: Button, selectedOption: String, correctAnswer: String) {
        isAnswered = true
        btnNext.isEnabled = true

        if (selectedOption == correctAnswer) {
            selectedBtn.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.GREEN)
            score++
        } else {
            selectedBtn.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.RED)

            buttons.find { it.text.toString().trim() == questions[index].getCorrectOption().trim() }
                ?.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.GREEN)
        }

        buttons.forEach { it.isEnabled = false }
    }


    private fun goToNextQuestion() {
        index++
        if (index < questions.size) {
            loadQuestion()
        } else {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("total", 30) // Total score is 30
        startActivity(intent)
        finish()
    }

    data class Question(
        val questionText: String,
        val optionA: String,
        val optionB: String,
        val optionC: String,
        val optionD: String,
        val correctAnswer: String
    ) {
        val options = listOf(optionA, optionB, optionC, optionD)

        fun getCorrectOption(): String {
            return options[correctAnswer[0] - 'A']
        }
    }
}
