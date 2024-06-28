package com.example.alne.Network

import com.example.alne.data.model.Food
import com.example.alne.data.model.UserId
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FridgeApi {

    @Multipart
    @POST("/fridge")
    fun addFridgeFood(
        @Header("accessToken") accessToken: String,
        @Part file: MultipartBody.Part,
        @Part("addIngredientDto") food: RequestBody
    ): Call<FridgePostResponse>


    @POST("/getFridge")
    fun getFridgeFood(
        @Header("accessToken") accessToken: String,
        @Body userId: UserId
    ): Call<FridgeGetResponse>

    @POST("/delFridge")
    fun deleteFridgeFood(
        @Body userId: UserId
    ): Call<FridgePostResponse>

}