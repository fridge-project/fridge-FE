package com.example.alne.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.GlobalApplication
import com.example.alne.data.model.Comment
import com.example.alne.data.model.DeleteFavorite
import com.example.alne.data.model.FavoriteRespond
import com.example.alne.data.model.RecipeProcess
import com.example.alne.data.model.Status
import com.example.alne.repository.recipeRepository
import com.example.alne.Network.AuthResponse
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.data.model.LikeRespond
import com.example.alne.data.model.Profile
import com.example.alne.data.model.ProfileRespond
import com.example.alne.data.model.addComment
import com.example.alne.room.model.recipe
import com.example.alne.utils.RESPONSE_STATUS
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Arrays

class RecipeDetailViewModel: ViewModel() {

    private val repository = recipeRepository()

    var itemComments = ArrayList<Comment>()

    //댓글 전체 목록 LiveData
    private val _usersCommentsLiveData = MutableLiveData<ArrayList<Comment>>()
    val usersCommentsLiveData: LiveData<ArrayList<Comment>> = _usersCommentsLiveData

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
        _addRecipeLikeLiveData.postValue(false)
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

    fun addUserComment(recipe_id: String, comment: addComment) = repository.addUserComment(recipe_id, comment).enqueue(object: Callback<JsonElement>{
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
    fun getRecipeProcess(recipeCode: Int) = repository.getRecipeProcess(recipeCode).enqueue(object: Callback<JsonArray>{
        override fun onResponse(
            call: Call<JsonArray>,
            response: Response<JsonArray>,
        ) {
            val res = response.body()
            when(response.code()){
                200 -> {
                    if (res != null) {
                        var item: ArrayList<RecipeProcess> = ArrayList()
                        for(json in res){
                            var jsonObject = json.asJsonObject
                            var recipe_code = jsonObject.get("recipe_code").asInt
                            var order_num = jsonObject.get("order_num").asInt
                            var detail = jsonObject.get("detail").asString
                            var imageURL = jsonObject.get("imageURL").asString
                            var tip = jsonObject.get("tip").asString
                            item.add(RecipeProcess(recipe_code,order_num,detail,imageURL,tip))
                        }
                        item = ArrayList(item.sortedBy { it.order_num })
                        _getRecipeProcessLiveData.postValue(item)
                    }
                }

                500 -> {

                }
            }
        }

        override fun onFailure(call: Call<JsonArray>, t: Throwable) {
            Log.d("getRecipeProcess_onFailure", t.message.toString())
        }

    })
//
//    fun addRecipeFavorite(recipeCode: Int ,userId: UserId = UserId(GlobalApplication.prefManager.getUserToken()?.accessToken?.toInt()!!, null)) = repository.addRecipeFavorite(recipeCode, userId).enqueue(object: Callback<FavoriteRespond>{
//        override fun onResponse(call: Call<FavoriteRespond>, response: Response<FavoriteRespond>) {
//            val res = response.body()
//            when(res?.status){
//                200 -> {
//                    _addRecipeFavoriteLiveData.postValue(res.data.favorite)
//                    Log.d("addRecipeFavorite_onSuccess", res.toString())
//                }
//                else -> {
//                    _addRecipeFavoriteLiveData.postValue(false)
//                    Log.d("addRecipeFavorite_onSuccess:fail", res.toString())
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<FavoriteRespond>, t: Throwable) {
//            Log.d("addRecipeFavorite_onFailure", t.message.toString())
//            _addRecipeFavoriteLiveData.postValue(false)
//        }
//
//    })
//
//    fun userLikeRecipe(recipeCode: Int, userId: UserId = UserId(GlobalApplication.prefManager.getUserToken()?.accessToken?.toInt()!!, null)) = repository.userLikeRecipe(recipeCode, userId).enqueue(object: Callback<LikeRespond>{
//        override fun onResponse(call: Call<LikeRespond>, response: Response<LikeRespond>) {
//            val res = response.body()
//            when(res?.status){
//                200 -> {
//                    _addRecipeLikeLiveData.postValue(res.data.like)
//                    Log.d("addRecipeFavorite_onSuccess", res.toString())
//                }
//                else -> {
//                    _addRecipeLikeLiveData.postValue(false)
//                    Log.d("addRecipeFavorite_onSuccess:fail", res.toString())
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<LikeRespond>, t: Throwable) {
//            _addRecipeLikeLiveData.postValue(false)
//            Log.d("addRecipeFavorite_fail", t.message.toString())
//        }
//
//    })

    fun likeRecipe(_id: String, completion: (RESPONSE_STATUS) -> Unit ) = repository.likeRecipe(_id).enqueue(object: Callback<String>{
        override fun onResponse(p0: Call<String>, response: Response<String>) {
            val res = response.body()
            when(response.code()){
                200 -> {
                    if(res.toString().equals("좋아요 성공")){
                        _addRecipeLikeLiveData.postValue(true)
                    }else{
                        _addRecipeLikeLiveData.postValue(false)
                    }
                    completion(RESPONSE_STATUS.OKAY)
                }

                500 -> {
                    completion(RESPONSE_STATUS.FAIL)
                }
            }
            Log.d("likeRecipe", res.toString())
        }
        override fun onFailure(p0: Call<String>, p1: Throwable) {
            Log.d("likeRecipe", p1.message.toString())
            completion(RESPONSE_STATUS.NETWORK_ERROR)
        }

    })


//    fun deleteRecipeFavorite(delete: DeleteFavorite) = authRepository.deleteRecipeFavorite(delete).enqueue(object: Callback<Status>{
//        override fun onResponse(call: Call<Status>, response: Response<Status>) {
//            val res = response.body()
//            when(res?.status){
//                200 -> {
//                    _deleteRecipeFavoriteLiveData.postValue(true)
//                    Log.d("deleteRecipeFavorite_onSuccess", res.toString())
//                }
//                else -> {
//                    _deleteRecipeFavoriteLiveData.postValue(false)
//                    Log.d("deleteRecipeFavorite_onSuccess:fail", res.toString())
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<Status>, t: Throwable) {
//            _deleteRecipeFavoriteLiveData.postValue(false)
//            Log.d("deleteRecipeFavorite_onFailure", t.message.toString())
//        }
//
//    })

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