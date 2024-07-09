package com.example.alne.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alne.GlobalApplication
import com.example.alne.Network.FridgePostResponse
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.repository.fridgeRepository
import com.example.alne.utils.RESPONSE_STATUS
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
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
    private val _getFridgeLiveData = MutableLiveData<ArrayList<FridgeIngredient>>()
    val getFridgeLiveData: LiveData<ArrayList<FridgeIngredient>> = _getFridgeLiveData

    // 현재 등록된 재료 아이템
    var fridgeItems = ArrayList<FridgeIngredient>()

    init {
        getFridgeFood()
    }

    // 재료 등록
    fun addItem(food: FridgeIngredient){
        fridgeItems.add(food)
        _getFridgeLiveData.postValue(fridgeItems)
    }

    // 재료 등록
    fun deleteItem(food: FridgeIngredient){
        fridgeItems.remove(food)
        _getFridgeLiveData.postValue(fridgeItems)
    }


    fun addFridgeDataTest(food: FridgeIngredient, completion: (RESPONSE_STATUS) -> Unit){
        repository.addFridgeDataTest(food).enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var res = response.body()
                when(response.code()){
                    200 -> {
                        addItem(food)
                        completion(RESPONSE_STATUS.OKAY)
                    }

                    500 -> {
                        completion(RESPONSE_STATUS.FAIL)
                    }
                }
                Log.d("addFridgeDataTest", res.toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.NETWORK_ERROR)
            }

        })
    }

    fun addFridgeData(food: FridgeIngredient, photoFile: File?){
        var fileBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), photoFile!!)
        var file: MultipartBody.Part = MultipartBody.Part.createFormData("file", photoFile!!.name, fileBody)
        var content: RequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(food))
        var accessToken = GlobalApplication.prefManager.getUserToken().accessToken
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
//                            getFridgeFood(getUserToken()?.accessToken!!, UserId(getUserToken()?.accessToken?.toInt()!!, null))

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

    fun getFridgeFood(){
        repository.getFridgeFood().enqueue(object: Callback<JsonArray>{
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>,
            ) {
                var res: JsonArray? = response.body()
                when(response.code()){
                    200 -> {
                        if (res != null) {
                            var items: ArrayList<FridgeIngredient> = ArrayList()
                            for(json in res){
                                var jsonObject = json.asJsonObject
                                var name = jsonObject.get("name").asString
                                var memo = jsonObject.get("memo").asString
                                var storage = jsonObject.get("storage").asString
                                var exp = jsonObject.get("exp").asString
                                var date = jsonObject.get("date").asString
                                var imageURL = jsonObject.get("imageURL").asString
                                items.add(
                                    FridgeIngredient(name,memo,storage,exp,date, imageURL)
                                )
                            }
                            fridgeItems.addAll(items)
                            _getFridgeLiveData.postValue(items)
                        }
                    }

                    401 -> {

                    }
                }
                Log.d("getFridgeFood", res.toString())
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("getFridgeFood", t.message.toString())
            }

        })
    }
//
//    fun deleteFridgeFood(userId: UserId){
//        repository.deleteFridgeFood(userId).enqueue(object: Callback<FridgePostResponse>{
//            override fun onResponse(
//                call: Call<FridgePostResponse>,
//                response: Response<FridgePostResponse>,
//            ) {
//                var res = response.body()
//                Log.d("deleteFridgeFood", res.toString())
//                    when(res?.status){
//                        200 -> {
//                            getFridgeFood(getUserToken()?.accessToken!!, UserId(getUserToken()?.accessToken?.toInt()!!, null))
//                        }
//                        else -> {
//                            Log.d("deleteFridgeFood", "onSuccess:fail")
//                        }
//                    }
//
//            }
//
//            override fun onFailure(call: Call<FridgePostResponse>, t: Throwable) {
//                Log.d("deleteFridgeFood:FAIL", t.message.toString())
//            }
//        })
//    }
}