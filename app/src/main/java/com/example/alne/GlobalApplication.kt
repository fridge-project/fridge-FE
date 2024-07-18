package com.example.alne

import android.app.Application
import android.util.Log
import com.example.alne.Network.RecipeApi
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.Recipe
import com.example.alne.room.AppDatabase
import com.example.alne.room.model.recipe
import com.example.alne.utils.SharedPrefManager
import com.kakao.sdk.common.KakaoSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class GlobalApplication():Application() {

    companion object {
        lateinit var prefManager: SharedPrefManager
        lateinit var appDatabase: AppDatabase
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("GlobalApplication", "GlobalApplication_Create")
        // 다른 초기화 코드들
        prefManager = SharedPrefManager(applicationContext)
        appDatabase = AppDatabase.getInstance(applicationContext)!!
        retrofit = getRetrofit()

        if(!prefManager.getfirstApp()){
            retrofit.create(RecipeApi::class.java).getAllRecipe().enqueue(object : Callback<ArrayList<Recipe>> {
                override fun onResponse(
                    p0: Call<ArrayList<Recipe>>,
                    p1: Response<ArrayList<Recipe>>,
                ) {
                    var res = p1.body()
                    when (p1.code()) {
                        200 -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                for (Recipe in res!!) {
                                    appDatabase.recipeDao().insertAllRecipe(
                                        recipe(
                                            Recipe._id,
                                            Recipe.recipe_code,
                                            Recipe.recipe,
                                            Recipe.introduce,
                                            Recipe.category_code,
                                            Recipe.category,
                                            Recipe.class_code,
                                            Recipe.classification,
                                            Recipe.time,
                                            Recipe.calorie,
                                            Recipe.serving,
                                            Recipe.difficulty,
                                            Recipe.imageURL,
                                            Recipe.detailURL
                                        ))
                                }
                            }
                        }

                        500 -> {

                        }
                    }

                    Log.d("Application", p1.body().toString())
                }

                override fun onFailure(p0: Call<ArrayList<Recipe>>, p1: Throwable) {

                }
            })
            prefManager.savefirstApp()
        }

        // Kakao SDK 초기화
        KakaoSdk.init(this, "ee52fc7baab1b48b61bcf3e7f3b617f0")
    }
}