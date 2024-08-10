package com.example.alne.view.Recipe.viewpage.review

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.alne.domain.model.recipe
import com.example.alne.view.Recipe.viewpage.review.viewpage.ReviewPageFragment
import com.example.alne.view.Recipe.viewpage.review.viewpage.StarPageFragment

class ProcessVPAdapter(fragment: Fragment, val recipe: recipe): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ReviewPageFragment(recipe)
            else -> StarPageFragment()
        }
    }
}