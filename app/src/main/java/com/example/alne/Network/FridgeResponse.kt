package com.example.alne.Network
import com.google.gson.annotations.SerializedName

data class FridgePostResponse(
    @SerializedName(value = "status") val status: Int,
    @SerializedName(value = "data") val data: Int
)