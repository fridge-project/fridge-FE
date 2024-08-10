package com.example.alne.view.Recipe

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.alne.databinding.FragmentRecipeBinding
import com.example.alne.domain.model.recipe
import com.example.alne.utils.Recipe_TYPE
import com.example.alne.viewmodel.RecipeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    lateinit var binding: FragmentRecipeBinding
    private val viewModel: RecipeViewModel by viewModels()
    lateinit var gridAdapter: RecipeGVAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRecipeBinding.inflate(layoutInflater)

        // 화면 밖 클릭 시 키보드 내림
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
        binding.recipeGv.addItemDecoration(GridSpacingItemDecoration(spanCount = 2, spacing = 20, true))
        binding.recipeGv.adapter = gridAdapter
        binding.recipeGv.layoutManager = GridLayoutManager(requireContext(), 2)

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


    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean,
    ) :
        ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount + 1      // 1부터 시작

            /** 첫번째 행(row-1)에 있는 아이템인 경우 상단에 [space] 만큼의 여백을 추가한다 */
            if (position < spanCount){
                outRect.top = spacing
            }
            /** 마지막 열(column-N)에 있는 아이템인 경우 우측에 [space] 만큼의 여백을 추가한다 */
            if (column == spanCount){
                outRect.right = spacing
            }
            /** 모든 아이템의 좌측과 하단에 [space] 만큼의 여백을 추가한다. */
            outRect.left = spacing
            outRect.bottom = spacing
        }
    }
}