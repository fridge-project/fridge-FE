package com.example.alne.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.alne.data.model.RoomIngredient

@Dao
interface IngredientDao {

    @Query("SELECT * FROM RoomIngredient WHERE name=:name")
    fun getIngredient(name: String): RoomIngredient

    @Insert
    suspend fun insertIngredient(ingredient: RoomIngredient)

    @Query("SELECT * FROM RoomIngredient")
    suspend fun getAll(): List<RoomIngredient>



}