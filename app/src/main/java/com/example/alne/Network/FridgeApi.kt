package com.example.alne.Network

import com.example.alne.model.Food
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FridgeApi {

    @POST("/fridge")
    fun addFridgeFood(@Body food: Food): Call<FridgePostResponse>

    @GET("/fridge")
    fun getFridgeFood(
        @Query("userId") userId: Int?
    ): Call<FridgeGetResponse>



}