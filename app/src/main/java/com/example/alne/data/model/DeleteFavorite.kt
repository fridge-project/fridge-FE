package com.example.alne.data.model

data class DeleteFavorite(
    val userId: Int,
    val data: Id
)

data class Id(
    val id: Int
)
