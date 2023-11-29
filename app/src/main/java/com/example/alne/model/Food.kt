package com.example.alne.model

data class Food(
    var userId: Int?,
    var name: String,
    var exp: String? = null,
    var memo: String? = null,
    var storage: String,
    var image: String?,
)

//enum class Storage {
//    COLD , FROZEN , ETC
//}
