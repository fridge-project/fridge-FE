package com.example.alne.data.model

data class Recipe(
    val recipeCode: Int,
    val name:String,
    val introduce: String,
    val category: String,
    val classification: String,
    val time: String,
    val serving: String,
    val difficulty: String,
    val imageURL: String,
    val calorie: Int,
    val favoritedByUser: List<Int>,
    val likedByUser: List<Int>
)

data class RecipeProcessRespond(
    val status: Int,
    val data: RecipeProcess
)

data class RecipeProcess (
    val recipe: Recipe,
    val recipeProcess: List<Process>,
    val comments: List<Comments>,
    val gradeDto: Grade,
    val favorite: Boolean,
    val like: Boolean
)

data class Comments(
    val imageURL: String,
    val grade: Int,
    val user: UserInfo,
    val detail: String
)

data class Process(
    val id: Int,
    val orderNum: Int,
    val detail: String
)
