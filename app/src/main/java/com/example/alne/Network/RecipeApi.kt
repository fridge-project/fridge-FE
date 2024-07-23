package com.example.alne.Network

import com.example.alne.data.model.Recipe
import com.example.alne.data.model.addComment
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RecipeApi {

    @GET(API.RECIPE_LIST)
    @Headers("Auth: false")
    fun getAllRecipe(): Call<ArrayList<Recipe>>

    @POST(API.ADD_COMMENT)
    fun addUserComment(
        @Path(value = "recipe_id") recipe_id: String,
        @Body comment: addComment
    ): Call<JsonElement>

    @PUT(API.UPDATE_COMMENT)
    fun updateUserComment(
        @Path(value = "id") commentId: String,
        @Body comment: addComment
    ): Call<JsonElement>

    @GET(API.RECIPE_PROECSS)
    @Headers("Auth: false")
    fun getRecipeProcess(
        @Path("recipe_code") recipeCode: Int,
    ): Call<JsonArray>

    @POST(API.FAVORITE)
    fun favoriteRecipe(
        @Path("recipe_id") recipe_id: String,
    ): Call<String>

    @POST(API.LIKE)
    fun likeRecipe(
        @Path("id") id: String
    ): Call<String>

    @GET(API.LIKE_LIST)
    fun userLikeList(): Call<JsonArray>

    @DELETE(API.DELETE_COMMENT)
    fun deleteUserComment(
        @Path("id") id: String
    ): Call<String>


    //사용자가 즐겨찾기 한 모든 레시피 정보 가져오기
    @GET(API.FAVORITE_LIST)
    fun userFavoriteList(): Call<JsonArray>
}