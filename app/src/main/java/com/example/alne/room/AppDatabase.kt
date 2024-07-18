package com.example.alne.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alne.room.dao.IngredientDao
import com.example.alne.room.dao.RecipeDao
import com.example.alne.room.model.food
import com.example.alne.room.model.recipe

@Database(entities = [food::class, recipe::class], version = 3)
abstract class AppDatabase : RoomDatabase(){

    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if(instance == null){
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database-name"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}