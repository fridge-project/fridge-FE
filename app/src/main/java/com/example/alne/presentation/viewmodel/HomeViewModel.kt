package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alne.GlobalApplication
import com.example.alne.Network.FridgeGetResponse
import com.example.alne.data.model.Food
import com.example.alne.data.model.UserId
import com.example.alne.repository.fridgeRepository
import com.example.alne.repository.recipeRepository
import com.example.alne.room.model.recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val recipeRepository = recipeRepository()
    private val fridgeRepository = fridgeRepository()


    private val _getRecipeLiveData = MutableLiveData<ArrayList<recipe>>()
    val getRecipeLiveData: LiveData<ArrayList<recipe>> = _getRecipeLiveData

    private val _getFridgeLiveData = MutableLiveData<ArrayList<Food>>()
    val getFridgeLiveData: LiveData<ArrayList<Food>> = _getFridgeLiveData



    init {
        viewModelScope.launch(Dispatchers.IO) {
//            _getRecipeLiveData.postValue(recipeRepository.getAllRecipe())
//            if(GlobalApplication.prefManager.getUserToken()?.accessToken != null){
//                getFridgeFood(GlobalApplication.prefManager.getUserToken()!!.accessToken!!, UserId(
//                    GlobalApplication.prefManager.getUserToken()!!.userId, null))
//            }

        }
    }

    fun getFridgeFood(accessToken: String, userId: UserId){
        Log.d("accessToken", accessToken)
        Log.d("userId", userId.toString())
        fridgeRepository.getFridgeFood(accessToken, userId).enqueue(object: Callback<FridgeGetResponse> {
            override fun onResponse(
                call: Call<FridgeGetResponse>,
                response: Response<FridgeGetResponse>,
            ) {
                Log.d("getFridgeFood", "onSuccess")
                if(response.isSuccessful) {
                    var res = response.body()
                    when(res?.status){
                        200 -> {
                            var fridge = res.data
                            var items: ArrayList<Food> = ArrayList()
                            for(item in fridge){
                                var name = item.ingredient.name
                                var image = item.ingredient.image
                                var storage = item.storage
                                var expire = item.exp
                                var memo = item.memo
                                var food = Food(item.ingredient.id, name, expire,item.addDate, memo, storage, image)
                                items.add(food)
                            }
                            _getFridgeLiveData.postValue(items)
                        }
                    }
                }else{
                    Log.d("getFridgeFood", response.body().toString())
                }
            }

            override fun onFailure(call: Call<FridgeGetResponse>, t: Throwable) {
                Log.d("getFridgeFood", t.message.toString())
            }

        })
    }
}