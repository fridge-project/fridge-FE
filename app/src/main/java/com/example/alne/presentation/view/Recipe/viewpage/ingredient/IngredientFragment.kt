package com.example.alne.view.Recipe.viewpage.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.databinding.FragmentIngredientBinding
import com.example.alne.viewmodel.RecipeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IngredientFragment : Fragment() {

    lateinit var binding: FragmentIngredientBinding
    private val viewModel: RecipeDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIngredientBinding.inflate(layoutInflater)

        viewModel.ingredientRecipeLiveData.observe(viewLifecycleOwner, Observer{
            binding.ingredientRv.adapter = IngredientRVAdapter(it)
            binding.ingredientRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.ingredientRv.addItemDecoration(DividerItemDecoration(context, 1))
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}