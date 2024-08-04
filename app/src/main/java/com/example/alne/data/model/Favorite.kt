package com.example.alne.data.model

data class Favorite(
    val _id: String,
    val user_id: String,
    val recipe_id: String
)

data class UserInfo(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val account: String,
    val image: String,
    val favoriteRecipes: List<Int> = ArrayList(),
    val likeRecipes: List<Int> = ArrayList()
)
