package com.example.demo_study_app// com.example.demo_study_app.LoginActivity.kt
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<TextView>(R.id.signupText)

        loginButton.setOnClickListener {
            // Get the email and password from the EditTexts
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(email = email, password = password, name="", username="")

                // Call the login API
                RetrofitClient.instance.login(user).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            // Show a success message
                            Toast.makeText(this@LoginActivity, "Login Successful: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                            // Navigate to MainActivity
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()  // Close the com.example.demo_study_app.LoginActivity
                        } else {
                            Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }
        // Navigate to Login
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
