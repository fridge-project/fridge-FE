package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.data.model.RecipeModel
import com.example.alne.domain.repository.recipeRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository: recipeRepository
) : ViewModel() {

    //재료 등록 정보
    private val _userCommentLiveData: MutableLiveData<ArrayList<RecipeModel>> = MutableLiveData<ArrayList<RecipeModel>>()
    val userCommentLiveData: LiveData<ArrayList<RecipeModel>>? = _userCommentLiveData

    init {
        repository.userCommentList().enqueue(object: Callback<JsonArray>{
            override fun onResponse(p0: Call<JsonArray>, p1: Response<JsonArray>) {
                val res = p1.body()!!
                when(p1.code()){
                    200 -> {
                        var item: ArrayList<RecipeModel> = ArrayList()
                        if (res.size() != 0) {
                            for(json in res){
                                var jsonObject = json.asJsonObject
                                var gson = Gson().fromJson(jsonObject, RecipeModel::class.java)
                                item.add(gson)
                            }
                            _userCommentLiveData.postValue(item)
                        }
                    }

                    500 -> {

                    }
                }
                Log.d("CommentViewModel", res.toString())
            }

            override fun onFailure(p0: Call<JsonArray>, p1: Throwable) {

            }
        })
    }
}