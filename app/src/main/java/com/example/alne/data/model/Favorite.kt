package com.example.alne.data.model
data class Favorite(
    val user: UserInfo,
    val recipe: Recipe,
    val favorite: Boolean
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
