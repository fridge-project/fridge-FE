package com.example.alne.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alne.GlobalApplication
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.data.model.Recipe
import com.example.alne.repository.recipeRepository
import com.example.alne.room.model.recipe
import com.example.alne.utils.RESPONSE_STATUS
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeListViewModel: ViewModel() {

    var repository = recipeRepository()

    private var _likeItemLiveData = MutableLiveData<ArrayList<Recipe>?>()
    var likeItemLiveData = _likeItemLiveData

    fun getUserLikeList(completion: (RESPONSE_STATUS) -> Unit) = repository.getUserLikeList().enqueue(object: Callback<JsonArray> {
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
                        _likeItemLiveData.postValue(item)
                    }
                    completion(RESPONSE_STATUS.OKAY)
                }

                500 -> {

                }
            }

            Log.d("getUserLikeList", res.toString())
        }

        override fun onFailure(p0: Call<JsonArray>, p1: Throwable) {
            Log.d("getUserLikeList", p1.message.toString())
        }

    })
}