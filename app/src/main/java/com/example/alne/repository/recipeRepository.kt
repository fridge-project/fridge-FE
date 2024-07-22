package com.example.alne.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.alne.GlobalApplication
import com.example.alne.Network.AuthApi
import com.example.alne.data.model.Comment
import com.example.alne.data.model.DeleteFavorite
import com.example.alne.room.model.recipe
import com.example.alne.Network.RecipeApi
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.Id
import com.example.alne.data.model.addComment
import com.example.alne.presentation.view.Recipe.RecipePagingSource
import com.example.alne.utils.Recipe_TYPE
import kotlinx.coroutines.flow.Flow

class recipeRepository {

    private val recipeService = getRetrofit().create(RecipeApi::class.java)
    private val authService = getRetrofit().create(AuthApi::class.java)


    // 레시피 목록 Paging
    fun getRecipePagingSource(category : String?, type: Recipe_TYPE): Flow<PagingData<recipe>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RecipePagingSource(category, type) }
        ).flow
    }

    fun addUserComment(recipe_id: String, comment: addComment) = recipeService.addUserComment(recipe_id, comment)

    fun deleteUserComment(_id: String) = recipeService.deleteUserComment(_id)

    fun updateUserComment(id: String, addComment: addComment) = recipeService.updateUserComment(id, addComment)

    //특정 레시피 조회
    fun getRecipeProcess(recipeCode: Int) = recipeService.getRecipeProcess(recipeCode)
//
//    fun addRecipeFavorite(recipeCode: Int,userId: UserId) = recipeService.addRecipeFavorite(recipeCode,userId)
//
//    fun userLikeRecipe(recipeCode: Int, userId: UserId) = recipeService.likeRecipe(recipeCode,userId)

    //좋아요 요청/해제
    fun likeRecipe(id: String) = recipeService.likeRecipe(id)

    //사용자 좋아요 전체 레시피 목록
    fun getUserLikeList() = recipeService.userLikeList()

    //사용자 즐겨찾기 전체 목록
    fun getUserFavorites(id: Id) = recipeService.getUserFavorites(id)

    fun deleteRecipeFavorite(delete: DeleteFavorite) = recipeService.deleteRecipeFavorite(delete)

//    fun getUserProfile(userId: UserId) = authService.getUserProfile(userId)
}