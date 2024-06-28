package com.example.alne.view.Recipe.viewpage.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.databinding.FragmentIngredientBinding
import com.example.alne.data.model.Ingredient


class IngredientFragment : Fragment() {

    lateinit var binding: FragmentIngredientBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIngredientBinding.inflate(layoutInflater)

        val items: ArrayList<Ingredient> = ArrayList()
        items.add(Ingredient(1, "계란","123", "1개"))
        items.add(Ingredient(1, "고사리","123", "20g"))
        items.add(Ingredient(1, "고추장","123", "1/2큰술"))
        items.add(Ingredient(1, "국간장","123", "약간"))
        items.add(Ingredient(1, "다진마늘","123", "약간"))
        items.add(Ingredient(1, "다진파","123", "약간"))
        items.add(Ingredient(1, "도라지","123", "20g"))
        items.add(Ingredient(1, "미나리","123", "20g"))
        items.add(Ingredient(1, "설탕","123", "약간"))
        items.add(Ingredient(1, "소금","123", "약간"))
        items.add(Ingredient(1, "숙주","123", "20g"))
        items.add(Ingredient(1, "양지머리","123", "100g"))
        items.add(Ingredient(1, "쌀","123", "4컵"))
        items.add(Ingredient(1, "안심","123", "200g"))
        items.add(Ingredient(1, "참기름","123", "약간"))
        items.add(Ingredient(1, "고춧가루","123", "1/2컵"))
        items.add(Ingredient(1, "콩나물","123", "20g"))


        binding.ingredientRv.adapter = IngredientRVAdapter(items)
        binding.ingredientRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ingredientRv.addItemDecoration(DividerItemDecoration(context, 1))


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}