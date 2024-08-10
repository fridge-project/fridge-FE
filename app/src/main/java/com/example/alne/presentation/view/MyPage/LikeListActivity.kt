package com.example.alne.presentation.view.MyPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alne.data.model.RecipeModel
import com.example.alne.databinding.ActivityLikeListBinding
import com.example.alne.presentation.viewmodel.LikeListViewModel
import com.example.alne.view.MyPage.RecipeGVAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeListActivity : AppCompatActivity() {

    lateinit var binding: ActivityLikeListBinding
    private val viewModel: LikeListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.likeListTb.setNavigationOnClickListener {
            finish()
        }

        val gridAdapter = RecipeGVAdapter(this)
        gridAdapter.setMyItemClickListener(object: RecipeGVAdapter.setOnClickListener{
            override fun clickItem(recipe: RecipeModel) {
//                var intent = Intent(this, RecipeDetailActivity::class.java)
//                intent.putExtra("recipe", Gson().toJson(favorite.recipe))
//                startActivity(intent)
            }
        })
        binding.likeListGv.adapter = gridAdapter

        viewModel.likeItemLiveData?.observe(this, Observer{
            if(!it.isNullOrEmpty()){
                gridAdapter.addItems(it)
            }
            Log.d("likeItemLiveData", it.toString())
        })


    }
}