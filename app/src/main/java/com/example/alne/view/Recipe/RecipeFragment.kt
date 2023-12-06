package com.example.alne.view.Recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.alne.databinding.FragmentRecipeBinding
import com.example.alne.model.Recipe

class RecipeFragment : Fragment() {
    lateinit var binding: FragmentRecipeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(layoutInflater)
        binding.recipeMenuAllTv.requestFocus()

        binding.recipeMenuAllTv.setOnClickListener {
            startActivity(Intent(requireContext(), RecipeDetailActivity::class.java))
        }


        val items = ArrayList<Recipe>()
        items.add(Recipe("라면", "--", "라면", "일식", "40분", "40분", "보통", "11"))
        items.add(Recipe("라면", "--", "라면", "일식", "40분", "40분", "보통", "11"))
        items.add(Recipe("라면", "--", "라면", "일식", "40분", "40분", "보통", "11"))
        items.add(Recipe("라면", "--", "라면", "일식", "40분", "40분", "보통", "11"))
        items.add(Recipe("라면", "--", "라면", "일식", "40분", "40분", "보통", "11"))
        items.add(Recipe("라면", "--", "라면", "일식", "40분", "40분", "보통", "11"))
        val gridAdapter = RecipeGVAdapter(requireContext(), items)
        binding.recipeGv.adapter = gridAdapter
        return binding.root
    }
}