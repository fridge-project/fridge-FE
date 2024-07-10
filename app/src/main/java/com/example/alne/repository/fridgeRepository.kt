package com.example.alne.repository

import com.example.alne.GlobalApplication
import com.example.alne.Network.FridgeApi
import com.example.alne.room.model.food
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.FridgeIngredient
import okhttp3.MultipartBody
import okhttp3.RequestBody

class fridgeRepository() {

    private val fridgeService = getRetrofit().create(FridgeApi::class.java)

    fun addFridgeData(accessToken: String, food: RequestBody, photoFile: MultipartBody.Part) = fridgeService.addFridgeFood(accessToken, photoFile, food)

    fun addFridgeDataTest(food: FridgeIngredient) = fridgeService.addFridgeFoodTest(food)

    fun getFridgeFood() = fridgeService.getFridgeFood()

    fun deleteFridgeFood(id: String) = fridgeService.deleteFridgeFood(id)

    suspend fun getAllIngredient(): ArrayList<food> = GlobalApplication.appDatabase.ingredientDao().getAll() as ArrayList<food>

}