package com.example.alne.presentation.view.MyPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alne.data.model.Recipe
import com.example.alne.databinding.ActivityCommentBinding
import com.example.alne.view.MyPage.RecipeGVAdapter
import com.example.alne.viewmodel.CommentViewModel

class CommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommentBinding
    lateinit var viewModel: CommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        setContentView(binding.root)

        binding.commentTb.setNavigationOnClickListener {
            finish()
        }

        val gridAdapter = RecipeGVAdapter(this)
        gridAdapter.setMyItemClickListener(object: RecipeGVAdapter.setOnClickListener{
            override fun clickItem(recipe: Recipe) {
//                var intent = Intent(this, RecipeDetailActivity::class.java)
//                intent.putExtra("recipe", Gson().toJson(favorite.recipe))
//                startActivity(intent)
            }
        })
        binding.commentGv.adapter = gridAdapter

        viewModel.userCommentLiveData?.observe(this, Observer{
            gridAdapter.addItems(it)
            Log.d("userCommentLiveData", it.toString())
        })




    }
}