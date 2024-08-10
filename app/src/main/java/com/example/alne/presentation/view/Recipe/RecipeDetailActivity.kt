package com.example.alne.view.Recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.alne.R
import com.example.alne.databinding.ActivityRecipeDetailBinding
import com.example.alne.domain.model.recipe
import com.example.alne.presentation.view.Recipe.CategoryRVAdapter
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.viewmodel.RecipeDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {


    private val viewModel: RecipeDetailViewModel by viewModels()
    lateinit var binding: ActivityRecipeDetailBinding
    private val information = arrayListOf("순서 및 후기", "재료","참고 영상")
    var globalrecipe: recipe? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recipeDetailTb.bringToFront()
        globalrecipe = Gson().fromJson(intent.getStringExtra("recipe"), recipe::class.java)

        Log.d("RecipeDetailActivity_recipe", globalrecipe.toString())


        viewModel.getRecipeProcess(globalrecipe!!.recipe_code, completion = {responseStatus ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY -> {
                    init(globalrecipe!!)
                }

                else -> {
                    Toast.makeText(this@RecipeDetailActivity, "데이터 불러오기 오류", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    private fun init(recipe: recipe){
        binding.recipeDetailTitleTv.text = recipe.recipe
        binding.recipeDetailChefTv.text = recipe.difficulty
        binding.recipeDetailIntroduceTv.text = recipe.introduce
        binding.recipeDetailTimeTv.text = "약 " + recipe.time
        binding.recipeDetailKcalTv.text = recipe.calorie
        Glide.with(this@RecipeDetailActivity).load(recipe.imageURL).into(binding.recipeDetailFoodIv)

        val recipeAdapter = RecipeDetailVPAdapter(this@RecipeDetailActivity, recipe)
        binding.recipeDetailVp.adapter = recipeAdapter
        TabLayoutMediator(binding.recipeDetailTl, binding.recipeDetailVp){ tab, position ->
            tab.text = information[position]
        }.attach()


        var categoryItem = ArrayList<String>()
        categoryItem.add(recipe.category)
        categoryItem.addAll(recipe.classification.split("/"))
        val categoryAdapter = CategoryRVAdapter(categoryItem)
        binding.recipeDetailCategoryRv.adapter = categoryAdapter
        binding.recipeDetailCategoryRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        viewModel.addRecipeLikeLiveData.observe(this, Observer {
            if(it){
                binding.like.setImageResource(R.drawable.like_on)
            }else {
                binding.like.setImageResource(R.drawable.like_off)
            }
        })

        viewModel.addRecipeFavoriteLiveData.observe(this, Observer{
            if(it){
                binding.bookmark.setImageResource(R.drawable.bookmark_on)
            }else {
                binding.bookmark.setImageResource(R.drawable.bookmark_off)
            }
        })

        binding.like.setOnClickListener {
            viewModel.likeRecipe(recipe._id)
        }

        binding.bookmark.setOnClickListener {
            viewModel.addRecipeFavorite(recipe._id)
        }
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.recipeDetailTb.setNavigationOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onBackPressed()
            }
        })
    }
}