package com.example.alne.data.model

data class FridgeIngredient(
    var _id: String?,
    val name: String,
    val memo: String,
    val storage: String,
    val exp: String,
    val date: String,
    val imageURL: String? = ""
)

