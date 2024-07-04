package com.example.alne.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alne.GlobalApplication
import com.example.alne.Network.FridgeGetResponse
import com.example.alne.Network.FridgePostResponse
import com.example.alne.data.model.Food
import com.example.alne.data.model.UserId
import com.example.alne.repository.fridgeRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FridgeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = fridgeRepository()

    //재료 등록 정보
    private val _getFridgeLiveData = MutableLiveData<ArrayList<Food>>()
    val getFridgeLiveData: LiveData<ArrayList<Food>> = _getFridgeLiveData


    init {
        if(getUserToken() != null){
            getFridgeFood(getUserToken()?.accessToken!!, UserId(getUserToken()?.accessToken?.toInt()!!, null))
        }
    }

    fun addFridgeData(accessToken: String, food: Food, photoFile: File?){
        var fileBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), photoFile!!)
        var file: MultipartBody.Part = MultipartBody.Part.createFormData("file", photoFile!!.name, fileBody)
        var content: RequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(food))
        repository.addFridgeData(accessToken,content, file).enqueue(object: Callback<FridgePostResponse>{
            override fun onResponse(
                call: Call<FridgePostResponse>,
                response: Response<FridgePostResponse>,
            ) {
                Log.d("addFridgeData", "onSuccess")
                Log.d("addFridgeData", response.body().toString())
                if(response.isSuccessful) {
                    var res = response.body()
                    when(res?.status){
                        200 -> {
                            Log.d("addFridgeData", "재료 등록 성공")
                            Log.d("addFridgeData", response.body()?.data.toString())
                            getFridgeFood(getUserToken()?.accessToken!!, UserId(getUserToken()?.accessToken?.toInt()!!, null))

                        }
                        401 -> {
                            Log.d("addFridgeData", "재료 등록 실패")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<FridgePostResponse>, t: Throwable) {
                Log.d("addFridgeData", "onFailure")
            }
        })
    }

    fun getFridgeFood(accessToken: String, userId: UserId){
        Log.d("accessToken", accessToken)
        Log.d("userId", userId.toString())
        repository.getFridgeFood(accessToken, userId).enqueue(object: Callback<FridgeGetResponse>{
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
                                var image = item.imageUrl
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

    fun deleteFridgeFood(userId: UserId){
        repository.deleteFridgeFood(userId).enqueue(object: Callback<FridgePostResponse>{
            override fun onResponse(
                call: Call<FridgePostResponse>,
                response: Response<FridgePostResponse>,
            ) {
                var res = response.body()
                Log.d("deleteFridgeFood", res.toString())
                    when(res?.status){
                        200 -> {
                            getFridgeFood(getUserToken()?.accessToken!!, UserId(getUserToken()?.accessToken?.toInt()!!, null))
                        }
                        else -> {
                            Log.d("deleteFridgeFood", "onSuccess:fail")
                        }
                    }

            }

            override fun onFailure(call: Call<FridgePostResponse>, t: Throwable) {
                Log.d("deleteFridgeFood:FAIL", t.message.toString())
            }
        })
    }

    fun getUserToken() = GlobalApplication.prefManager.getUserToken()
}