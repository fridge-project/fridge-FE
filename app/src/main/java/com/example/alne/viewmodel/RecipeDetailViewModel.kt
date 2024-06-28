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
import com.example.alne.data.model.RecipeProcessRespond
import com.example.alne.data.model.Status
import com.example.alne.data.model.UserId
import com.example.alne.repository.recipeRepository
import com.example.alne.Network.AuthResponse
import com.example.alne.data.model.LikeRespond
import com.example.alne.data.model.Profile
import com.example.alne.data.model.ProfileRespond
import com.example.alne.data.model.requestComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailViewModel: ViewModel() {
    private val repository = recipeRepository()


    //댓글 조회
    private val _usersCommentsLiveData = MutableLiveData<Comment>()
    val usersCommentsLiveData: LiveData<Comment> = _usersCommentsLiveData

    //댓글 작성
    private val _addUserCommentLiveData = MutableLiveData<Boolean>()
    val addUserCommentLiveData: LiveData<Boolean> = _addUserCommentLiveData

    //댓글 삭제
    private val _delUserCommentLiveData = MutableLiveData<Boolean>()
    val delUserCommentLiveData: LiveData<Boolean> = _delUserCommentLiveData

    //특정 레시프 조회
    private val _getRecipeProcessLiveData = MutableLiveData<RecipeProcess>()
    val getRecipeProcessLiveData: LiveData<RecipeProcess> = _getRecipeProcessLiveData

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

    fun addUserComment(comment: Comment) = repository.addUserComment(comment).enqueue(object: Callback<AuthResponse>{
        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
            var res = response.body()
            when(res?.status){
                200 -> {
                    if(res.data == 1){
                        _addUserCommentLiveData.postValue(true)
                    }else{
                        _addUserCommentLiveData.postValue(false)
                    }
                    Log.d("addUserComment_res", res.toString())
                }
            }
        }

        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
            Log.d("addUserComment_onFailure", t.message.toString())
            _addUserCommentLiveData.postValue(false)
        }
    })


    //특정 레시피 조회
    fun getRecipeProcess(recipeCode: Int, userId: UserId) = repository.getRecipeProcess(recipeCode, userId).enqueue(object: Callback<RecipeProcessRespond>{
        override fun onResponse(
            call: Call<RecipeProcessRespond>,
            response: Response<RecipeProcessRespond>,
        ) {
            val res = response.body()
            when(res?.status){
                200 -> {
                    Log.d("getRecipeProcess_onSuccess", res.toString())
                    _getRecipeProcessLiveData.postValue(res.data)
                    _addRecipeLikeLiveData.postValue(res.data.like)
                    _addRecipeFavoriteLiveData.postValue(res.data.favorite)
                }
            }
        }

        override fun onFailure(call: Call<RecipeProcessRespond>, t: Throwable) {
            Log.d("getRecipeProcess_onFailure", t.message.toString())
        }

    })

    fun addRecipeFavorite(recipeCode: Int ,userId: UserId = UserId(GlobalApplication.prefManager.getUserToken()?.accessToken?.toInt()!!, null)) = repository.addRecipeFavorite(recipeCode, userId).enqueue(object: Callback<FavoriteRespond>{
        override fun onResponse(call: Call<FavoriteRespond>, response: Response<FavoriteRespond>) {
            val res = response.body()
            when(res?.status){
                200 -> {
                    _addRecipeFavoriteLiveData.postValue(res.data.favorite)
                    Log.d("addRecipeFavorite_onSuccess", res.toString())
                }
                else -> {
                    _addRecipeFavoriteLiveData.postValue(false)
                    Log.d("addRecipeFavorite_onSuccess:fail", res.toString())
                }
            }
        }

        override fun onFailure(call: Call<FavoriteRespond>, t: Throwable) {
            Log.d("addRecipeFavorite_onFailure", t.message.toString())
            _addRecipeFavoriteLiveData.postValue(false)
        }

    })

    fun userLikeRecipe(recipeCode: Int, userId: UserId = UserId(GlobalApplication.prefManager.getUserToken()?.accessToken?.toInt()!!, null)) = repository.userLikeRecipe(recipeCode, userId).enqueue(object: Callback<LikeRespond>{
        override fun onResponse(call: Call<LikeRespond>, response: Response<LikeRespond>) {
            val res = response.body()
            when(res?.status){
                200 -> {
                    _addRecipeLikeLiveData.postValue(res.data.like)
                    Log.d("addRecipeFavorite_onSuccess", res.toString())
                }
                else -> {
                    _addRecipeLikeLiveData.postValue(false)
                    Log.d("addRecipeFavorite_onSuccess:fail", res.toString())
                }
            }
        }

        override fun onFailure(call: Call<LikeRespond>, t: Throwable) {
            _addRecipeLikeLiveData.postValue(false)
            Log.d("addRecipeFavorite_fail", t.message.toString())
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

    fun deleteUserComment(requestComment: requestComment) = repository.deleteUserComment(requestComment).enqueue(object: Callback<AuthResponse>{
        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
            val res = response.body()
            when(res?.status){
                200 -> {
                    Log.d("deleteUserComment", "Success")
//                    getRecipeProcess(requestComment.data, UserId(GlobalApplication.prefManager.getUserToken()?.userId!!, null))
                    _delUserCommentLiveData.postValue(true)
                }
                else -> {
                    _delUserCommentLiveData.postValue(false)
                    Log.d("deleteUserComment", "Fail")
                }
            }
        }

        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
            _delUserCommentLiveData.postValue(false)
            Log.d("deleteUserComment_onFailure", t.message.toString())
        }

    })

    fun getUserProfile(userId: Int) = repository.getUserProfile(UserId(userId,null)).enqueue(object: Callback<ProfileRespond>{
        override fun onResponse(call: Call<ProfileRespond>, response: Response<ProfileRespond>) {
            val res = response.body()
            when(res?.status){
                200 -> {
                    _userProfileLiveData.postValue(res.data)
                    Log.d("getUserProfile", "onResponse: {${res.data}")
                }
                else -> {
                    _userProfileLiveData.postValue(null)
                    Log.d("getUserProfile", "onResponse: Fail")
                }
            }
        }

        override fun onFailure(call: Call<ProfileRespond>, t: Throwable) {
            _userProfileLiveData.postValue(null)
            Log.d("getUserProfile", "onFailure: {${t.message.toString()}")
        }
    })


}