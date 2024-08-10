package com.example.alne.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.alne.GlobalApplication
import com.example.alne.GlobalApplication.Companion.prefManager
import com.example.alne.data.Network.RecipeApi
import com.example.alne.data.model.RecipeModel
import com.example.alne.data.model.RoomIngredient
import com.example.alne.domain.model.recipe
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private var recipeService: RecipeApi
): ViewModel() {

    private val TAG = "StartViewModel"

    fun saveRecipeData() {
        if (!prefManager.getfirstApp()) {
            recipeService.getAllRecipe().enqueue(object : Callback<ArrayList<RecipeModel>> {
                override fun onResponse(
                    p0: Call<ArrayList<RecipeModel>>,
                    p1: Response<ArrayList<RecipeModel>>,
                ) {
                    var res = p1.body()
                    when (p1.code()) {
                        200 -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                for (Recipe in res!!) {
                                    GlobalApplication.appDatabase.recipeDao().insertAllRecipe(
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
                                        )
                                    )
                                }
                            }
                            prefManager.savefirstApp()
                        }

                        500 -> {

                        }
                    }
                    Log.d(TAG, res.toString())
                }

                override fun onFailure(p0: Call<ArrayList<RecipeModel>>, p1: Throwable) {

                }
            })
        }
    }

    fun saveIngredientData(){
        recipeService.ingredientsList().enqueue(object : Callback<JsonArray> {
            override fun onResponse(p0: Call<JsonArray>, p1: Response<JsonArray>) {
                val res = p1.body()
                when (p1.code()) {
                    200 -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            for (json in res!!) {
                                var jsonObject = json.asJsonObject
                                var _id = jsonObject.get("_id").asString
                                var name = jsonObject.get("name").asString
                                GlobalApplication.appDatabase.ingredientDao().insertIngredient(
                                    RoomIngredient(_id, name)
                                )
                            }
                        }
                    }
                    500 -> {

                    }
                }
                Log.d(TAG, res.toString())
            }

            override fun onFailure(p0: Call<JsonArray>, p1: Throwable) {

            }

        })
    }

}