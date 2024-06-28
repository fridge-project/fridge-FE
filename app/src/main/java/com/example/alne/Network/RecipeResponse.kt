package com.example.alne.Network

import com.example.alne.data.model.Jwt
import com.example.alne.data.model.Recipe
import com.google.gson.annotations.SerializedName


data class RecipeResponse(
    @SerializedName(value = "status") val status: Int,
    @SerializedName(value = "data") val data: ArrayList<Recipe>
)