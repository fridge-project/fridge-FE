package com.example.alne.view.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alne.databinding.FragmentHomeBinding
import com.example.alne.data.model.Food
import com.example.alne.room.model.recipe
import com.example.alne.view.Recipe.RecipeDetailActivity
import com.example.alne.view.Recipe.RecipeGVAdapter
import com.example.alne.viewmodel.HomeViewModel
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    var items: ArrayList<recipe> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

//        viewModel.getFridgeLiveData.observe(viewLifecycleOwner, Observer {
//            var adapter = ExpireAdapter(it)
//            binding.homeItemRv.adapter = adapter
//        })


        val adapter = HomeRecipeRankRVAdapter(requireContext())
        adapter.setMyItemClickListener(object: HomeRecipeRankRVAdapter.setOnClickListener{
            override fun clickItem(recipe: recipe) {
                var intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipe", Gson().toJson(recipe))
                startActivity(intent)
            }
        })
        binding.homeRecipeRankRv.adapter = adapter
        binding.homeItemRv.adapter = adapter

        viewModel.getRecipeLiveData.observe(viewLifecycleOwner, Observer {data ->
            Log.d("viewModel" , data.toString())
            items.addAll(data)
            adapter.addItems(items)
        })

        return binding.root
    }

    fun saveQuery(item: String){
        val sharedPreferences = context?.getSharedPreferences("search_query", MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString("query",item)
        edit?.commit()
    }


}