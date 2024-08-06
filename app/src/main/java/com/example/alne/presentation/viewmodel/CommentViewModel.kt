package com.example.alne.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.Network.RecipeService
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.Recipe
import com.example.alne.data.model.repository.RecipeRepositoryImpl
import com.google.gson.Gson
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel: ViewModel() {

    private val repository = RecipeRepositoryImpl(getRetrofit().create(RecipeService::class.java))

    //재료 등록 정보
    private val _userCommentLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()
    val userCommentLiveData: LiveData<ArrayList<Recipe>>? = _userCommentLiveData

    init {

        repository.userCommentList().enqueue(object: Callback<JsonArray>{
            override fun onResponse(p0: Call<JsonArray>, p1: Response<JsonArray>) {
                val res = p1.body()!!
                when(p1.code()){
                    200 -> {
                        var item: ArrayList<Recipe> = ArrayList()
                        if (res.size() != 0) {
                            for(json in res){
                                var jsonObject = json.asJsonObject
                                var gson = Gson().fromJson(jsonObject, Recipe::class.java)
                                item.add(gson)
                            }
                            _userCommentLiveData.postValue(item)
                        }
                    }

                    500 -> {

                    }
                }
            }

            override fun onFailure(p0: Call<JsonArray>, p1: Throwable) {

            }

        })
    }



}