package com.example.demo_study_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        nameEditText = findViewById(R.id.name)
        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        signupButton = findViewById(R.id.signupButton)
        loginText = findViewById(R.id.loginText)

        // Handle Signup
        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                signup(User(name, username, email, password))
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to Login
        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signup(user: User) {
        RetrofitClient.instance.signup(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignupActivity, "Signup Successful! Please Login.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignupActivity, "Signup Failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
