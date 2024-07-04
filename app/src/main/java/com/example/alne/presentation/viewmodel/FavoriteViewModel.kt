package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.GlobalApplication
import com.example.alne.data.model.Favorite
import com.example.alne.data.model.FavoriteRespond
import com.example.alne.data.model.FavoritesRespond
import com.example.alne.data.model.Food
import com.example.alne.data.model.Id
import com.example.alne.data.model.Recipe
import com.example.alne.data.model.UserId
import com.example.alne.repository.recipeRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel: ViewModel() {

    val TAG = "FavoriteViewModel"

    private val repository = recipeRepository()

    //재료 등록 정보
    private val _userFavoriteLiveData: MutableLiveData<ArrayList<Favorite>>? = MutableLiveData<ArrayList<Favorite>>()
    val userFavoriteLiveData: LiveData<ArrayList<Favorite>>? = _userFavoriteLiveData

    init {
//        authRepository.getUserFavorites(Id(GlobalApplication.prefManager.getUserToken()?.userId!!)).enqueue(object: Callback<FavoritesRespond>{
//            override fun onResponse(
//                call: Call<FavoritesRespond>,
//                response: Response<FavoritesRespond>,
//            ) {
//                var res = response.body()
//                Log.d(TAG, "onResponse: ${res.toString()}")
//                when(res?.status){
//                    200 -> {
//                        Log.d(TAG, "onResponse: Success")
//                        _userFavoriteLiveData?.postValue(res.data as ArrayList<Favorite>)
//                    }
//                    else -> {
//                        Log.d(TAG, "onResponse: Success_Fail")
//                        _userFavoriteLiveData?.postValue(null)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<FavoritesRespond>, t: Throwable) {
//                Log.d(TAG, "onFailure: ${t.message.toString()}")
//                _userFavoriteLiveData?.postValue(null)
//            }
//
//        })
    }



}