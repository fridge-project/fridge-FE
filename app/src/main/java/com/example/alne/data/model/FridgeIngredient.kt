package com.example.alne.data.model

data class FridgeIngredient(
    var _id: String?,
    var name: String,
    var memo: String,
    var storage: String,
    var exp: String,
    val date: String,
    var imageURL: String? = ""
)

