package com.example.demo_study_app// com.example.demo_study_app.ApiService.kt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.ResponseBody

// ApiService.kt
data class User(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)

data class LoginResponse(val message: String)

interface ApiService {
    @POST("/signup")
    fun signup(@Body user: User): Call<ResponseBody>

    @POST("/login")
    fun login(@Body user: User): Call<LoginResponse>
}
