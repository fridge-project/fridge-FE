package com.example.alne.view.MyPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alne.databinding.ActivityFavoriteBinding
import com.example.alne.data.model.RecipeModel
import com.example.alne.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteTb.setNavigationOnClickListener {
            onBackPressed()
        }

        val gridAdapter = RecipeGVAdapter(this)
        gridAdapter.setMyItemClickListener(object: RecipeGVAdapter.setOnClickListener{
            override fun clickItem(recipe: RecipeModel) {
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