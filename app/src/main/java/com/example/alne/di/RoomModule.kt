package com.example.alne.di

import android.content.Context
import androidx.room.Room
import com.example.alne.data.room.AppDatabase
import com.example.alne.data.room.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object RoomModule {
//
//    @Provides
//    fun provideRecipeDatabase(@ApplicationContext context: Context): AppDatabase{
//        return Room.databaseBuilder(context, AppDatabase::class.java, "fridge").build()
//    }
//
//    @Provides
//    fun providesRecipeDao(recipeDatabase: AppDatabase): RecipeDao {
//        return recipeDatabase.recipeDao()
//    }
//}