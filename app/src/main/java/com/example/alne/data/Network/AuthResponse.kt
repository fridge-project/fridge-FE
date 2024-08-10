package com.example.alne.data.Network

import com.example.alne.data.model.Jwt
import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName(value = "status") val status: Int,
    @SerializedName(value = "data") val data: Int
)

data class SignUpResponse(
    @SerializedName(value = "message") val message: String,
)

data class LoginResponse(
    @SerializedName(value = "accessToken") val accessToken: String,
    @SerializedName(value = "refreshToken") val refreshToken: String
)
