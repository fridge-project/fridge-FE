package com.example.alne.Network

import com.example.alne.data.model.Comment
import com.example.alne.data.model.DeleteFavorite
import com.example.alne.data.model.FavoriteRespond
import com.example.alne.data.model.FavoritesRespond
import com.example.alne.data.model.Id
import com.example.alne.data.model.LikeRespond
import com.example.alne.data.model.RecipeProcessRespond
import com.example.alne.data.model.UserId
import com.example.alne.data.model.Status
import com.example.alne.data.model.requestComment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RecipeApi {

    @GET("/recipe")
    fun getAllRecipe(): Call<RecipeResponse>

    @POST("/addComment")
    fun addUserComment(
        @Body comment: Comment
    ): Call<AuthResponse>

    @POST("/recipe/{recipeCode}")
    fun getRecipeProcess(
        @Path("recipeCode") recipeCode: Int,
        @Body userId: UserId
    ): Call<RecipeProcessRespond>

    @POST("/recipe/{recipeCode}/favorite")
    fun addRecipeFavorite(
        @Path("recipeCode") recipeCode: Int,
        @Body userId: UserId
    ): Call<FavoriteRespond>

    @POST("/favorites/delete")
    fun deleteRecipeFavorite(
        @Body delete: DeleteFavorite
    ): Call<Status>

    @POST("/recipe/{recipeCode}/like")
    fun likeRecipe(
        @Path("recipeCode") recipeCode: Int,
        @Body userid: UserId
    ): Call<LikeRespond>

    @POST("/delComment")
    fun deleteUserComment(
        @Body requestComment: requestComment
    ): Call<AuthResponse>


    //사용자가 즐겨찾기 한 모든 레시피 정보 가져오기
    @POST("/favorites")
    fun getUserFavorites(
        @Body id: Id
    ): Call<FavoritesRespond>




}