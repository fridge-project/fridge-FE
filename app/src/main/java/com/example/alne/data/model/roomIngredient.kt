package com.example.alne.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomIngredient(
    @PrimaryKey val _id: String,
    val name: String,
)
