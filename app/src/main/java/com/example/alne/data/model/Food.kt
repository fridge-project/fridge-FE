package com.example.alne.data.model

import java.io.File

data class Food(
    var userId: Int?,
    var name: String,
    var exp: String? = null,
    var addDate: String? = null,
    var memo: String? = null,
    var storage: String,
    var imageUrl: String? = "https://fridgeproject.s3.ap-northeast-2.amazonaws.com/default.png"
)

data class addIngrdientFood(
    var userId: Int?,
    var name: String,
    var exp: String? = null,
    var addDate: String? = null,
    var memo: String? = null,
    var storage: String,
)