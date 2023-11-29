package com.example.alne.Network

import com.example.alne.model.Fridge
import com.google.gson.annotations.SerializedName

data class FridgePostResponse(
    @SerializedName(value = "status") val status: Int,
    @SerializedName(value = "data") val data: Int
)

data class FridgeGetResponse(
    @SerializedName(value = "status") val status: Int,
    @SerializedName(value = "data") val data: Array<Fridge>
)
