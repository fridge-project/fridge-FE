package com.example.alne.data.Network

import com.example.alne.data.model.Comment
import com.example.alne.data.model.Favorite
import com.example.alne.data.model.Ingredient
import com.example.alne.data.model.Like
import com.example.alne.data.model.RecipeProcess

data class RecipeDetailResponse(
    val process: ArrayList<RecipeProcess>,
    val recipe_ingredient: ArrayList<Ingredient>,
    val updatedComments: ArrayList<Comment>,
    val like: ArrayList<Like>,
    val favorite: ArrayList<Favorite>,
    val likeCount: Int,
    val favoriteCount: Int,
    val gradeArr: Array<Int>,
    val username: String,
    val email: String
)
