package com.example.alne.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.alne.room.model.food

@Dao
interface IngredientDao {

    @Query("SELECT * FROM food WHERE name=:name")
    fun getIngredient(name: String): food

    @Query("SELECT * FROM food")
    suspend fun getAll(): List<food>



}