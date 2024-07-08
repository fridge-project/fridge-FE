package com.example.alne.data.model

data class FridgeIngredient(
    val name: String,
    val memo: String,
    val storage: String,
    val exp: String,
    val date: String,
    val imageURL: String? = "https://fridgeproject.s3.ap-northeast-2.amazonaws.com/default.png"
)

