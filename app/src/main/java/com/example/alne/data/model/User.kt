package com.example.alne.data.model


data class User(
    val email : String,
    val username: String?,
    val password: String,
)

data class Status(
    val status: Int
)