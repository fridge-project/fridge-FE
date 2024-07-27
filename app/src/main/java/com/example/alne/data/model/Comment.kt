package com.example.alne.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    var _id: String,
    var user_id: String,
    var recipe_id: String,
    var detail: String,
    var grade: Int,
    var __v: Int
)

data class addComment(
    var detail: String,
    var grade: Int,
    var ImageURL: String
)
