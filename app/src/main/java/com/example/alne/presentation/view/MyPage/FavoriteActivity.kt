package com.example.alne.view.MyPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.alne.R
import com.example.alne.databinding.ActivityFavoriteBinding
import com.example.alne.data.model.Favorite
import com.example.alne.data.model.Recipe
import com.example.alne.room.model.recipe
import com.example.alne.view.Recipe.RecipeDetailActivity
import com.example.alne.viewmodel.FavoriteViewModel
import com.google.gson.Gson

class FavoriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavoriteBinding
    lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.favoriteTb.setNavigationOnClickListener {
            onBackPressed()
        }

        val gridAdapter = RecipeGVAdapter(this)
        gridAdapter.setMyItemClickListener(object: RecipeGVAdapter.setOnClickListener{
            override fun clickItem(recipe: Recipe) {
//                var intent = Intent(this, RecipeDetailActivity::class.java)
//                intent.putExtra("recipe", Gson().toJson(favorite.recipe))
//                startActivity(intent)
            }
        })
        binding.recipeGv.adapter = gridAdapter

        viewModel.userFavoriteLiveData?.observe(this, Observer{
            if(!it.isNullOrEmpty()){
                gridAdapter.addItems(it)
            }
        })
    }
}