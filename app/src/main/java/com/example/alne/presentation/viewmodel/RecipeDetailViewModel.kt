package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.Network.RecipeService
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.Comment
import com.example.alne.data.model.RecipeProcess
import com.example.alne.data.model.repository.RecipeRepositoryImpl
import com.example.alne.data.model.Profile
import com.example.alne.data.model.addComment
import com.example.alne.domain.model.RecipeDetailResponse
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailViewModel(): ViewModel() {

    private val repository: RecipeRepositoryImpl = RecipeRepositoryImpl(getRetrofit().create(RecipeService::class.java))

    // 댓글 목록
    var itemComments = ArrayList<Comment>()

    //댓글 전체 목록 LiveData
    private val _usersCommentsLiveData = MutableLiveData<ArrayList<Comment>>()
    val usersCommentsLiveData: LiveData<ArrayList<Comment>> = _usersCommentsLiveData

    //평점 LiveData
    private val _usersStarLiveData = MutableLiveData<Int>()
    val usersStarLiveData: LiveData<Int> = _usersStarLiveData

    //특정 레시프 조회
    private val _getRecipeProcessLiveData = MutableLiveData<ArrayList<RecipeProcess>>()
    val getRecipeProcessLiveData: LiveData<ArrayList<RecipeProcess>> = _getRecipeProcessLiveData

    //즐겨찾기 등록
    private val _addRecipeFavoriteLiveData = MutableLiveData<Boolean>()
    val addRecipeFavoriteLiveData: LiveData<Boolean> = _addRecipeFavoriteLiveData

    //좋아요 등록
    private val _addRecipeLikeLiveData = MutableLiveData<Boolean>()
    val addRecipeLikeLiveData: LiveData<Boolean> = _addRecipeLikeLiveData

    //사용자 프로필
    private val _userProfileLiveData = MutableLiveData<Profile>()
    val userProfileLiveData: LiveData<Profile> = _userProfileLiveData

    init {
//        if(GlobalApplication.prefManager.getUserToken()?.userId != null){
//            getUserProfile(GlobalApplication.prefManager.getUserToken()!!.userId)
//        }else{
//            _userProfileLiveData.postValue(null)
//        }
    }

    fun addCommentItem(comment: Comment){
        itemComments.add(comment)
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
    fun getRecipeProcess(recipeCode: Int) = repository.getRecipeDetail(recipeCode).enqueue(object: Callback<RecipeDetailResponse>{
        override fun onResponse(
            call: Call<RecipeDetailResponse>,
            response: Response<RecipeDetailResponse>,
        ) {
            val res = response.body()
            when(response.code()){
                200 -> {
                    if(res?.like != null) _addRecipeLikeLiveData.postValue(true) else _addRecipeLikeLiveData.postValue(false)
                    if(res?.favorite != null) _addRecipeFavoriteLiveData.postValue(true) else _addRecipeFavoriteLiveData.postValue(false)
                    if(res?.comment != null) addCommentItem(res?.comment)
                    if(res?.avg != null) _usersStarLiveData.postValue(res?.avg)
                    _getRecipeProcessLiveData.postValue(ArrayList(res?.process?.sortedBy { it.order_num }))
                }

                500 -> {

                }
            }
            Log.d("getRecipeProcess", res.toString())

        }

        override fun onFailure(call: Call<RecipeDetailResponse>, t: Throwable) {
            Log.d("getRecipeProcess_onFailure", t.message.toString())
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

    fun deleteUserComment(position: Int) = repository.deleteUserComment(itemComments[position]._id).enqueue(object: Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val res = response.body()
            when(response.code()){
                200 -> {
                    Log.d("deleteUserComment", "Success")
                    deleteCommentItem(position)
                }
                500 -> {
                    Log.d("deleteUserComment", "Fail")
                }
            }
            Log.d("deleteUserComment", res.toString())
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
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