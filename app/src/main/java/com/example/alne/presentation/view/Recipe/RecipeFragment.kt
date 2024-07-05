package com.example.alne.view.Recipe

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
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
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.databinding.FragmentRecipeBinding
import com.example.alne.room.model.recipe
import com.example.alne.utils.Recipe_TYPE
import com.example.alne.utils.fromDpToPx
import com.example.alne.viewmodel.RecipeViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class RecipeFragment : Fragment() {

    lateinit var binding: FragmentRecipeBinding
    lateinit var viewModel: RecipeViewModel
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
        binding.root.setOnTouchListener(View.OnTouchListener { _, _ ->
            hideKeyBoard()
            false
        })

        gridAdapter = RecipeGVAdapter(requireContext())
        gridAdapter.setMyItemClickListener(object: RecipeGVAdapter.setOnClickListener{
            override fun clickItem(recipe: recipe) {
                var intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipe", Gson().toJson(recipe))
                startActivity(intent)
            }
        })
        binding.recipeGv.addItemDecoration(GridSpacingItemDecoration(spanCount = 2, spacing = 12f.fromDpToPx()))
        binding.recipeGv.adapter = gridAdapter

        // Flow 데이터 수신 대기
        lifecycleScope.launch {
            viewModel.getRecipePagingList(null,Recipe_TYPE.DEFAULT).collectLatest {
                gridAdapter.submitData(it)
            }
        }

         //카테고리 메뉴 설정
        binding.recipeRadioGp.setOnCheckedChangeListener { radioGroup, i ->
            hideKeyBoard()
            if(i == binding.recipeRadioAll.id){
                categoryChanged(Recipe_TYPE.DEFAULT, null)
            }else if(i == binding.recipeRadioKorean.id){
                categoryChanged(Recipe_TYPE.CATEGORY1, "한식")
            }else if(i == binding.recipeRadioChinese.id){
                categoryChanged(Recipe_TYPE.CATEGORY1, "중국")
            }else if(i == binding.recipeRadioJapanese.id){
                categoryChanged(Recipe_TYPE.CATEGORY1, "일본")
            }else if(i == binding.recipeRadioWestern.id){
                categoryChanged(Recipe_TYPE.CATEGORY1, "이탈리아")
            }else if(i == binding.recipeRadioEtc.id){
                categoryChanged(Recipe_TYPE.CATEGORY2, "퓨전", "동남아시아")
            }
        }

        // 검색 SearchView 설정
        binding.recipeSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                categoryChanged(Recipe_TYPE.SEARCH, newText)
                return false
            }

        })
        return binding.root
    }

    private fun categoryChanged(type: Recipe_TYPE, vararg category: String?) {
        lifecycleScope.launch {
            if(category == null){
                viewModel.getRecipePagingList(null, type).collectLatest {
                    gridAdapter.submitData(it)
                }
            }
            if(category.size == 1){
                viewModel.getRecipePagingList(category[0], type).collectLatest {
                    gridAdapter.submitData(it)
                }
            }

            if(category.size == 2){
                viewModel.getRecipePagingList(category[0]+ " " + category[1], type).collectLatest {
                    gridAdapter.submitData(it)
                }
            }
        }
    }

    //키보드 숨기기 및 포커스 없애기
     private fun hideKeyBoard(){
        if(activity != null && requireActivity().currentFocus != null){
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            requireActivity().window.decorView.clearFocus();
        }
    }


    internal class GridSpacingItemDecoration(
        private val spanCount: Int, // Grid의 column 수
        private val spacing: Int // 간격
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position: Int = parent.getChildAdapterPosition(view)

            if (position >= 0) {
                val column = position % spanCount // item column
                outRect.apply {
                    // spacing - column * ((1f / spanCount) * spacing)
                    left = spacing - column * spacing / spanCount
                    // (column + 1) * ((1f / spanCount) * spacing)
                    right = (column + 1) * spacing / spanCount
                    if (position < spanCount) top = spacing
                    bottom = spacing
                }
            } else {
                outRect.apply {
                    left = 0
                    right = 0
                    top = 0
                    bottom = 0
                }
            }
        }
    }
}