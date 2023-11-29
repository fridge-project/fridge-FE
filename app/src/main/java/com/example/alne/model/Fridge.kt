package com.example.alne.model

import com.example.flo.model.User

data class Fridge(
    val user: User,
    val ingredient: Ingredient,
    val exp: String,
    val storage: String,
    val memo: String,
)
