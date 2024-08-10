package com.example.alne.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alne.domain.model.recipe


@Dao
interface RecipeDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRecipe(recipe: recipe)

    @Query("SELECT * FROM recipe LIMIT 10 OFFSET (:page-1)*10")
    suspend fun getAll(page: Int): List<recipe>

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipe(): List<recipe>

    @Query("SELECT * FROM recipe WHERE category = :category LIMIT 10 OFFSET (:page-1)*10")
    suspend fun getList(page: Int, category: String?): List<recipe>

    @Query("SELECT * FROM recipe WHERE category =:category1 OR category=:category2 LIMIT 10 OFFSET (:page-1)*10")
    suspend fun getList1(page: Int, category1: String?, category2: String?): List<recipe>

//    @Query("SELECT * FROM recipe WHERE name = :name LIMIT 10 OFFSET (:page-1)*10")
//    suspend fun getListName(page: Int, name: String?): List<recipe>

//    @Query("SELECT * FROM recipe WHERE category=:")
//    fun getRecipeCategory(category: String)
}