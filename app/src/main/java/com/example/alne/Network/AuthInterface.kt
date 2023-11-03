package com.example.flo.Network

import com.example.flo.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {

    @POST("/signup")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/login")
    fun login(@Body user: User): Call<AuthResponse>

}