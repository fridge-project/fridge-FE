package com.example.alne.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.alne.room.model.food
import com.example.alne.room.model.recipe


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe LIMIT 10 OFFSET (:page-1)*10")
    suspend fun getAll(page: Int): List<recipe>

    @Query("SELECT * FROM recipe WHERE category = :category LIMIT 10 OFFSET (:page-1)*10")
    suspend fun getList(page: Int, category: String?): List<recipe>

    @Query("SELECT * FROM recipe WHERE name = :name LIMIT 10 OFFSET (:page-1)*10")
    suspend fun getListName(page: Int, name: String?): List<recipe>

//    @Query("SELECT * FROM recipe WHERE category=:")
//    fun getRecipeCategory(category: String)
}