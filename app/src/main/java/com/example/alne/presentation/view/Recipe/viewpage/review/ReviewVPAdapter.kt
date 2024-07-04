package com.example.alne.view.Recipe.viewpage.review

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.alne.room.model.recipe
import com.example.alne.view.Recipe.viewpage.video.VideoFragment
import com.example.alne.view.Recipe.viewpage.ingredient.IngredientFragment
import com.example.alne.view.Recipe.viewpage.review.viewpage.ReviewPageFragment
import com.example.alne.view.Recipe.viewpage.review.viewpage.StarPageFragment

class ReviewVPAdapter(fragment: Fragment, val recipe: recipe): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ReviewPageFragment(recipe)
            else -> StarPageFragment()
        }
    }
}