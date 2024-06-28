package com.example.alne.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("recipeId") val recipeId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("detail") val detail: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("imageURL") val imageURL: String
)

data class requestComment(
    val userId: Int,
    val data: Int
)
