package com.example.alne.repository

import com.example.alne.GlobalApplication
import com.example.alne.Network.FridgeApi
import com.example.alne.data.model.UserId
import com.example.alne.room.model.food
import com.example.alne.Network.getRetrofit
import okhttp3.MultipartBody
import okhttp3.RequestBody

class fridgeRepository() {

    private val fridgeService = getRetrofit().create(FridgeApi::class.java)

    fun addFridgeData(accessToken: String, food: RequestBody, photoFile: MultipartBody.Part) = fridgeService.addFridgeFood(accessToken, photoFile, food)


    fun getFridgeFood(accessToken: String, userId: UserId) = fridgeService.getFridgeFood(accessToken,userId)

    fun deleteFridgeFood(userId: UserId) = fridgeService.deleteFridgeFood(userId)

    fun getAllIngredient(): ArrayList<food> = GlobalApplication.appDatabase.ingredientDao().getAll() as ArrayList<food>

}