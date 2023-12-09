package com.example.alne.view.Recipe

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.alne.view.Recipe.viewpage.ingredient.IngredientFragment
import com.example.alne.view.Recipe.viewpage.review.ReviewFragment
import com.example.alne.view.Recipe.viewpage.video.VideoFragment

class RecipeDetailVPAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ReviewFragment()
            1 -> IngredientFragment()
            else -> VideoFragment()
        }
    }
}