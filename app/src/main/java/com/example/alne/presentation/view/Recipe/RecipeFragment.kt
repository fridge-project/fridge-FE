package com.example.alne.view.Recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.alne.databinding.FragmentRecipeBinding
import com.example.alne.room.model.recipe
import com.example.alne.utils.Recipe_TYPE
import com.example.alne.viewmodel.RecipeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeFragment : Fragment() {
    lateinit var binding: FragmentRecipeBinding
    lateinit var viewModel: RecipeViewModel
    lateinit var items: PagingData<recipe>
    lateinit var gridAdapter: RecipeGVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        binding.root.setOnClickListener{
            hideKeyBoard()
        }

        gridAdapter = RecipeGVAdapter(requireContext())
        gridAdapter.setMyItemClickListener(object: RecipeGVAdapter.setOnClickListener{
            override fun clickItem(recipe: recipe) {
                var intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipe", Gson().toJson(recipe))
                startActivity(intent)
            }
        })
        binding.recipeGv.adapter = gridAdapter

        lifecycleScope.launch {
            viewModel.getRecipePagingList(null,Recipe_TYPE.DEFAULT).collectLatest {
                items = it
                gridAdapter.submitData(it)
            }
        }

         //카테고리 메뉴 설정
        binding.recipeRadioGp.setOnCheckedChangeListener { radioGroup, i ->
            if(i == binding.recipeRadioAll.id){
                lifecycleScope.launch{
                    gridAdapter.submitData(items)
                }
            }else if(i == binding.recipeRadioKorean.id){
                categoryChanged("한식")
            }else if(i == binding.recipeRadioChinese.id){
                categoryChanged("중국")
            }else if(i == binding.recipeRadioJapanese.id){
                categoryChanged("일본")
            }else if(i == binding.recipeRadioWestern.id){
                categoryChanged("이탈리아")
            }else if(i == binding.recipeRadioEtc.id){
                categoryChanged("퓨전", "동남아시아")
            }
        }

        // 검색 SearchView 설정
        binding.recipeSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    gridAdapter.submitData(items.filter {
                        it.name.equals(newText)
                    })
                }
//                gridAdapter.filter.filter(newText)
                return false
            }

        })
        return binding.root
    }

    private fun categoryChanged(category: String?) {
        lifecycleScope.launch {
            if(category == null){
                viewModel.getRecipePagingList(category, Recipe_TYPE.DEFAULT).collectLatest {
                    gridAdapter.submitData(it)
                }
            }else{
                viewModel.getRecipePagingList(category, Recipe_TYPE.CATEGORY1).collectLatest {
                    gridAdapter.submitData(it)
                }
            }
        }
    }

    private fun categoryChanged(category1: String, category2: String) {
//        var filterList: ArrayList<recipe> = ArrayList()
//        for (recipe in items) {
//            if(recipe.category.equals(category1) || recipe.category.equals(category2) ){
//                filterList.add(recipe)
//            }
//        }
//        lifecycleScope.launch {
//            viewModel.getRecipePagingList(category).collectLatest {
//                gridAdapter.submitData(it)
//            }
//        }
    }

    private fun hideKeyBoard(){
        if(activity != null && requireActivity().currentFocus != null){
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}