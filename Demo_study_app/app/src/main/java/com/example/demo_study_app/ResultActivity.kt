package com.example.demo_study_app
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("score", 0)
        val totalQuestions = 30

        val tvScore = findViewById<TextView>(R.id.tvScore)
        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val btnHome = findViewById<Button>(R.id.btnHome)

        tvScore.text = "Score: $score / $totalQuestions"

        saveResult(score, totalQuestions)

        btnRetry.setOnClickListener {
            startActivity(Intent(this, MasterQuiz::class.java))
            finish()
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun saveResult(score: Int, total: Int) {
        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("score", score)
            put("total", total)
        }

        val mediaType = "application/json".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/api/results")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

            }

            override fun onFailure(call: Call, e: IOException) {

            }
        })
    }
}