package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.data.Network.RecipeDetailResponse
import com.example.alne.data.model.Comment
import com.example.alne.data.model.Ingredient
import com.example.alne.data.model.RecipeProcess
import com.example.alne.data.model.addComment
import com.example.alne.domain.repository.recipeRepository
import com.example.alne.utils.RESPONSE_STATUS
import com.google.gson.Gson
import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: recipeRepository
) : ViewModel() {

    // 댓글 목록
    var itemComments = ArrayList<Comment>()

    var nickname: String = ""
    var email: String = ""

    //댓글 전체 목록 LiveData
    private val _usersCommentsLiveData = MutableLiveData<ArrayList<Comment>>()
    val usersCommentsLiveData: LiveData<ArrayList<Comment>> = _usersCommentsLiveData

    //평점 LiveData
    private val _usersStarLiveData = MutableLiveData<Array<Int>>()
    val usersStarLiveData: LiveData<Array<Int>> = _usersStarLiveData

    //특정 레시프 조회
    private val _getRecipeProcessLiveData = MutableLiveData<ArrayList<RecipeProcess>>()
    val getRecipeProcessLiveData: LiveData<ArrayList<RecipeProcess>> = _getRecipeProcessLiveData

    //레피피 재료 LiveData
    private val _ingredientRecipeLiveData = MutableLiveData<ArrayList<Ingredient>>()
    val ingredientRecipeLiveData: LiveData<ArrayList<Ingredient>> = _ingredientRecipeLiveData


    //즐겨찾기 등록
    private val _addRecipeFavoriteLiveData = MutableLiveData<Boolean>()
    val addRecipeFavoriteLiveData: LiveData<Boolean> = _addRecipeFavoriteLiveData

    //좋아요 등록
    private val _addRecipeLikeLiveData = MutableLiveData<Boolean>()
    val addRecipeLikeLiveData: LiveData<Boolean> = _addRecipeLikeLiveData


    fun addCommentItem(comment: Comment){
        itemComments.add(comment)
        _usersCommentsLiveData.postValue(itemComments)
    }

    fun addAllCommentItem(comment: ArrayList<Comment>){
        itemComments.clear()
        itemComments.addAll(comment)
        _usersCommentsLiveData.postValue(itemComments)
    }

    fun deleteCommentItem(position: Int){
        itemComments.removeAt(position)
        _usersCommentsLiveData.postValue(itemComments)
    }

    fun updateCommentItem(comment: Comment, position: Int){
        itemComments[position] = comment
        Log.d("updateCommentItem", comment.toString())
        _usersCommentsLiveData.postValue(itemComments)
    }

    fun addUserComment(recipe_id: String, comment: addComment) {
        repository.addUserComment(recipe_id, comment).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var res = response.body()
                when(response.code()){
                    201 -> {
                        var json = res?.asJsonObject
                        var comment = Gson().fromJson(json, Comment::class.java)
                        comment.email = email
                        comment.username = nickname
                        addCommentItem(comment)
                    }

                    500 -> {

                    }
                }
                Log.d("addUserComment_res", res.toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("addUserComment_onFailure", t.message.toString())
            }
        })
    }

    fun updateUserComment(position: Int, addComment: addComment) = repository.updateUserComment(itemComments[position]._id, addComment).enqueue(object: Callback<JsonElement>{
        override fun onResponse(p0: Call<JsonElement>, p1: Response<JsonElement>) {
            val res = p1.body()
            when(p1.code()){
                200 -> {
                    var json = res?.asJsonObject
                    var comment = Gson().fromJson(json, Comment::class.java)
                    updateCommentItem(comment, position)
                }

                500 -> {

                }
            }
            Log.d("updateUserComment", res.toString())
        }

        override fun onFailure(p0: Call<JsonElement>, p1: Throwable) {

        }

    })

    //특정 레시피 조회
    fun getRecipeProcess(recipeCode: Int, completion: (RESPONSE_STATUS) -> Unit) = repository.getRecipeDetail(recipeCode).enqueue(object: Callback<RecipeDetailResponse>{
        override fun onResponse(
            call: Call<RecipeDetailResponse>,
            response: Response<RecipeDetailResponse>,
        ) {
            val res = response.body()!!
            when(response.code()){
                200 -> {
                    if(res.like?.size != 0) _addRecipeLikeLiveData.postValue(true) else _addRecipeLikeLiveData.postValue(false)
                    if(res.favorite?.size != 0) _addRecipeFavoriteLiveData.postValue(true) else _addRecipeFavoriteLiveData.postValue(false)
                    if(res.updatedComments?.size != 0) addAllCommentItem(res.updatedComments!!)
                    _usersStarLiveData.postValue(res?.gradeArr)
                    nickname = res.username
                    email = res.email
                    _getRecipeProcessLiveData.postValue(ArrayList(res?.process?.sortedBy { it.order_num }))
                    _ingredientRecipeLiveData.postValue(res.recipe_ingredient)
                    completion(RESPONSE_STATUS.OKAY)
                }

                500 -> {
                    completion(RESPONSE_STATUS.FAIL)
                }
            }
            Log.d("getRecipeProcess", res.toString())

        }

        override fun onFailure(call: Call<RecipeDetailResponse>, t: Throwable) {
            Log.d("getRecipeProcess_onFailure", t.message.toString())
            completion(RESPONSE_STATUS.NETWORK_ERROR)
        }

    })

    fun addRecipeFavorite(recipe_id: String) = repository.favoriteRecipe(recipe_id).enqueue(object: Callback<String>{
        override fun onResponse(p0: Call<String>, p1: Response<String>) {
            val res = p1.body()
            when(p1.code()){
                200 -> {
                    if(res.toString().equals("즐겨찾기 성공")){
                        _addRecipeFavoriteLiveData.postValue(true)
                    }else{
                        _addRecipeFavoriteLiveData.postValue(false)
                    }
                    Log.d("addRecipeFavorite", "성공")
                }

                500 -> {
                    Log.d("addRecipeFavorite", "실패")
                }
            }
            Log.d("addRecipeFavorite", res.toString())
        }

        override fun onFailure(p0: Call<String>, p1: Throwable) {
            Log.d("addRecipeFavorite", p1.message.toString())
        }
    })
    fun likeRecipe(_id: String) = repository.likeRecipe(_id).enqueue(object: Callback<String>{
        override fun onResponse(p0: Call<String>, response: Response<String>) {
            val res = response.body()
            when(response.code()){
                200 -> {
                    if(res.toString().equals("좋아요 성공")){
                        _addRecipeLikeLiveData.postValue(true)
                    }else{
                        _addRecipeLikeLiveData.postValue(false)
                    }
                }

                500 -> {

                }
            }
            Log.d("likeRecipe", res.toString())
        }
        override fun onFailure(p0: Call<String>, p1: Throwable) {
            Log.d("likeRecipe", p1.message.toString())
        }

    })

    fun deleteUserComment(position: Int, completion: (RESPONSE_STATUS) -> Unit) = repository.deleteUserComment(itemComments[position]._id).enqueue(object: Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val res = response.body()
            when(response.code()){
                200 -> {
                    Log.d("deleteUserComment", "Success")
                    deleteCommentItem(position)
                    completion(RESPONSE_STATUS.OKAY)
                }
                500 -> {
                    Log.d("deleteUserComment", "Fail")
                    completion(RESPONSE_STATUS.FAIL)
                }
            }
            Log.d("deleteUserComment", res.toString())
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            completion(RESPONSE_STATUS.NETWORK_ERROR)
            Log.d("deleteUserComment_onFailure", t.message.toString())
        }

    })

//    fun getUserProfile(userId: Int) = repository.getUserProfile(UserId(userId,null)).enqueue(object: Callback<ProfileRespond>{
//        override fun onResponse(call: Call<ProfileRespond>, response: Response<ProfileRespond>) {
//            val res = response.body()
//            when(res?.status){
//                200 -> {
//                    _userProfileLiveData.postValue(res.data)
//                    Log.d("getUserProfile", "onResponse: {${res.data}")
//                }
//                else -> {
//                    _userProfileLiveData.postValue(null)
//                    Log.d("getUserProfile", "onResponse: Fail")
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<ProfileRespond>, t: Throwable) {
//            _userProfileLiveData.postValue(null)
//            Log.d("getUserProfile", "onFailure: {${t.message.toString()}")
//        }
//    })


}