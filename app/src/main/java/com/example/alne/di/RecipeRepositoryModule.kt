package com.example.alne.di

import com.example.alne.domain.repository.authRepository
import com.example.alne.domain.repository.fridgeRepository
import com.example.alne.domain.repository.recipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class RecipeRepositoryModule {

    @Provides
    fun recipeRepository(): recipeRepository {
        return recipeRepository()
    }

    @Provides
    fun fridgeRepository(): fridgeRepository {
        return fridgeRepository()
    }

    @Provides
    fun authRepository(): authRepository {
        return authRepository()
    }
}