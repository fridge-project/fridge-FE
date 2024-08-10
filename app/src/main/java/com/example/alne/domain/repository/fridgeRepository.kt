package com.example.alne.domain.repository

import com.example.alne.GlobalApplication
import com.example.alne.data.Network.FridgeApi
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.data.model.RoomIngredient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class fridgeRepository @Inject constructor(
    private val fridgeService: FridgeApi
) {
    fun addFridgeData(accessToken: String, food: RequestBody, photoFile: MultipartBody.Part) = fridgeService.addFridgeFood(accessToken, photoFile, food)

    fun addFridgeDataTest(food: FridgeIngredient) = fridgeService.addFridgeFoodTest(food)

    fun getFridgeFood() = fridgeService.getFridgeFood()

    fun deleteFridgeFood(id: String) = fridgeService.deleteFridgeFood(id)

    fun updateFridgeFood(food: FridgeIngredient) = fridgeService.updateFridgeFood(food._id!!, food)

    suspend fun getAllIngredient(): ArrayList<RoomIngredient> = GlobalApplication.appDatabase.ingredientDao().getAll() as ArrayList<RoomIngredient>

}