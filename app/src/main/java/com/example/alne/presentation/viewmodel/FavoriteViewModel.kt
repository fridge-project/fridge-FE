package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.data.model.Favorite
import com.example.alne.data.model.Recipe
import com.example.alne.repository.recipeRepository
import com.example.alne.room.model.recipe
import com.google.gson.Gson
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel: ViewModel() {

    private val repository = recipeRepository()

    //재료 등록 정보
    private val _userFavoriteLiveData: MutableLiveData<ArrayList<Recipe>> = MutableLiveData<ArrayList<Recipe>>()
    val userFavoriteLiveData: LiveData<ArrayList<Recipe>>? = _userFavoriteLiveData

    init {

        repository.userFavoriteList().enqueue(object: Callback<JsonArray>{
            override fun onResponse(p0: Call<JsonArray>, p1: Response<JsonArray>) {
                val res = p1.body()
                when(p1.code()){
                    200 -> {
                        var item: ArrayList<Recipe> = ArrayList()
                        if (!res!!.equals(null)) {
                            for(json in res){
                                var jsonObject = json.asJsonObject
                                var gson = Gson().fromJson(jsonObject, Recipe::class.java)
                                item.add(gson)
                            }
                            _userFavoriteLiveData.postValue(item)
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