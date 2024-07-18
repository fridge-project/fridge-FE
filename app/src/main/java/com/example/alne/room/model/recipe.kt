package com.example.alne.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class recipe (
    @PrimaryKey var _id: String,
    var recipe_code: Int,
    var recipe: String,
    var introduce: String,
    var category_code: Int,
    var category: String,
    var class_code: Int,
    var classification: String,
    var time: String,
    var calorie: String,
    var serving: String,
    var difficulty: String,
    var imageURL: String,
    var detailURL: String
)