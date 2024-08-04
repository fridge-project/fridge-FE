package com.example.alne.view.Recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.alne.R
import com.example.alne.databinding.ActivityRecipeDetailBinding
import com.example.alne.room.model.recipe
import com.example.alne.domain.utils.RESPONSE_STATUS
import com.example.alne.viewmodel.RecipeDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class RecipeDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecipeDetailBinding
    lateinit var viewModel: RecipeDetailViewModel
    private val information = arrayListOf("순서 및 후기", "재료","참고 영상")
    var globalrecipe: recipe? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recipeDetailTb.bringToFront()
        globalrecipe = Gson().fromJson(intent.getStringExtra("recipe"), recipe::class.java)

        Log.d("RecipeDetailActivity_recipe", globalrecipe.toString())
        viewModel = ViewModelProvider(this).get(RecipeDetailViewModel::class.java)
        viewModel.getRecipeProcess(globalrecipe!!.recipe_code, completion = {responseStatus ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY -> {
                    init(globalrecipe!!)
                }

                else -> {

                }
            }

        })
    }
    private fun init(recipe: recipe){
        binding.recipeDetailTitleTv.text = recipe.recipe
        binding.recipeDetailChefTv.text = recipe.difficulty
        binding.recipeDetailIntroduceTv.text = recipe.introduce
        binding.recipeDetailCategoryTv1.text = "#" + recipe.classification
        binding.recipeDetailTimeTv.text = "약 " + recipe.time + "분"
        binding.recipeDetailKcalTv.text = recipe.calorie.toString() + "kcal"
        Glide.with(this@RecipeDetailActivity).load(recipe.imageURL).into(binding.recipeDetailFoodIv)

        val recipeAdapter = RecipeDetailVPAdapter(this@RecipeDetailActivity, recipe)
        binding.recipeDetailVp.adapter = recipeAdapter
        TabLayoutMediator(binding.recipeDetailTl, binding.recipeDetailVp){ tab, position ->
            tab.text = information[position]
        }.attach()

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