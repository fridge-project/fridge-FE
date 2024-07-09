package com.example.alne.Network

import com.example.alne.data.model.FridgeIngredient
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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

    @POST(API.FRIDGE)
    fun addFridgeFoodTest(
        @Body food: FridgeIngredient
    ): Call<JsonElement>


    @GET(API.FRIDGE)
    fun getFridgeFood(): Call<JsonArray>

//    @POST("/delFridge")
//    fun deleteFridgeFood(
//        @Body userId: UserId
//    ): Call<FridgePostResponse>

}