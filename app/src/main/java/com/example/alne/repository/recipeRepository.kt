package com.example.alne.repository

import com.example.alne.GlobalApplication
import com.example.alne.Network.AuthApi
import com.example.alne.data.model.Comment
import com.example.alne.data.model.DeleteFavorite
import com.example.alne.data.model.UserId
import com.example.alne.room.model.recipe
import com.example.alne.Network.RecipeApi
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.Id
import com.example.alne.data.model.requestComment
import retrofit2.http.Body

class recipeRepository {

    private val recipeService = getRetrofit().create(RecipeApi::class.java)
    private val authService = getRetrofit().create(AuthApi::class.java)

    fun getAllRecipe(): ArrayList<recipe> = GlobalApplication.appDatabase.recipeDao().getAll() as ArrayList<recipe>



//    fun getUsersComments(): Arra
    fun addUserComment(comment: Comment) = recipeService.addUserComment(comment)

    fun deleteUserComment(requestComment: requestComment) = recipeService.deleteUserComment(requestComment)

    //특정 레시피 조회
    fun getRecipeProcess(recipeCode: Int, userId: UserId) = recipeService.getRecipeProcess(recipeCode, userId)

    fun addRecipeFavorite(recipeCode: Int,userId: UserId) = recipeService.addRecipeFavorite(recipeCode,userId)

    fun userLikeRecipe(recipeCode: Int, userId: UserId) = recipeService.likeRecipe(recipeCode,userId)

    //사용자 즐겨찾기 전체 목록
    fun getUserFavorites(id: Id) = recipeService.getUserFavorites(id)

    fun deleteRecipeFavorite(delete: DeleteFavorite) = recipeService.deleteRecipeFavorite(delete)

    fun getUserProfile(userId: UserId) = authService.getUserProfile(userId)
}