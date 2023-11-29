package com.example.flo.Network

import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName(value = "status") val status: Int,
    @SerializedName(value = "data") val data: Int
)