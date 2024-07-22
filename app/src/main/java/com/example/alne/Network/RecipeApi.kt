package com.example.alne.Network

import com.example.alne.data.model.Comment
import com.example.alne.data.model.DeleteFavorite
import com.example.alne.data.model.FavoriteRespond
import com.example.alne.data.model.FavoritesRespond
import com.example.alne.data.model.Id
import com.example.alne.data.model.LikeRespond
import com.example.alne.data.model.Recipe
import com.example.alne.data.model.Status
import com.example.alne.data.model.addComment
import com.example.alne.room.model.recipe
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


//
//    @POST("/recipe/{recipeCode}/favorite")
//    fun addRecipeFavorite(
//        @Path("recipeCode") recipeCode: Int,
//        @Body userId: UserId
//    ): Call<FavoriteRespond>

    @POST("/favorites/delete")
    fun deleteRecipeFavorite(
        @Body delete: DeleteFavorite
    ): Call<Status>

//    @POST("/recipe/{recipeCode}/like")
//    fun likeRecipe(
//        @Path("recipeCode") recipeCode: Int,
//        @Body userid: UserId
//    ): Call<LikeRespond>

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
    @POST("/favorites")
    fun getUserFavorites(
        @Body id: Id
    ): Call<FavoritesRespond>






}