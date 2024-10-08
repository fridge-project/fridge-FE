package com.example.alne.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.alne.domain.model.recipe
import com.example.alne.data.Network.RecipeApi
import com.example.alne.data.model.addComment
import com.example.alne.presentation.view.Recipe.RecipePagingSource
import com.example.alne.utils.Recipe_TYPE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class recipeRepository @Inject constructor(
    private val recipeService: RecipeApi
) {

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

    // 댓글 추가
    fun addUserComment(recipe_id: String, comment: addComment) = recipeService.addUserComment(recipe_id, comment)

    //특정 레시피 조회
    fun getRecipeDetail(recipeCode: Int) = recipeService.getRecipeDetail(recipeCode)

    // 댓글 삭제
    fun deleteUserComment(_id: String) = recipeService.deleteUserComment(_id)

    // 댓글 수정
    fun updateUserComment(id: String, addComment: addComment) = recipeService.updateUserComment(id, addComment)

    //즐겨찾기 등록
    fun favoriteRecipe(recipe_id: String) = recipeService.favoriteRecipe(recipe_id)

    //좋아요 요청/해제
    fun likeRecipe(id: String) = recipeService.likeRecipe(id)

    //사용자 좋아요 전체 레시피 목록
    fun userLikeList() = recipeService.userLikeList()

    //사용자 즐겨찾기 전체 목록
    fun userFavoriteList() = recipeService.userFavoriteList()

    fun userCommentList() = recipeService.userCommentList()

//    fun getUserProfile(userId: UserId) = authService.getUserProfile(userId)
}