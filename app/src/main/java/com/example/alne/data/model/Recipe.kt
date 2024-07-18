package com.example.alne.data.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    var _id: String,
    var recipe_code: Int,
    var recipe: String,
    var introduce: String,
    var category_code: Int,
    var category: String,
    var class_code: Int,
    @SerializedName("class") var classification: String,
    var time: String,
    var calorie: String,
    var serving: String,
    var difficulty: String,
    var imageURL: String,
    var detailURL: String
)
data class RecipeProcess (
    val recipe_code: Int,
    val order_num: Int,
    val detail: String,
    val imageURL: String,
    val tip: String,
)