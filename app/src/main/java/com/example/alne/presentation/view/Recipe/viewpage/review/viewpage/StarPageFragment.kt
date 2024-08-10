package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.alne.databinding.FragmentStarPageBinding
import com.example.alne.viewmodel.RecipeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarPageFragment : Fragment() {

    lateinit var binding: FragmentStarPageBinding
    private val viewModel: RecipeDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("starPageFragment", "onCreateView")
        binding = FragmentStarPageBinding.inflate(layoutInflater)
        viewModel.usersStarLiveData.observe(viewLifecycleOwner, Observer{ it ->
            Log.d("Star", it.toString())
            binding.startPageRatingbar.rating = it[0].toFloat()
            binding.starPageAverageStar.text = it[0].toString()
            setRatingProgress(it)
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setRatingProgress(grade: Array<Int>){
        var size = 0;
        for(i in 1 until grade.size){
            size += grade[i]
        }
        if(size == 0) size = 1
        var mod = 100 / size
        Log.d("progress", size.toString() + ":" + grade[1] / size)
        with(binding){

            expireProgressPb.progress = grade[1] * mod
            binding.expireProgressCountTv.text = grade[1].toString()


            expireProgressPb1.progress = grade[2] * mod
            binding.expireProgressCountTv1.text = grade[2].toString()


            expireProgressPb2.progress = grade[3] * mod
            binding.expireProgressCountTv2.text = grade[3].toString()


            expireProgressPb3.progress = grade[4] * mod
            binding.expireProgressCountTv3.text = grade[4].toString()


            expireProgressPb4.progress = grade[5] * mod
            binding.expireProgressCountTv4.text = grade[5].toString()
        }
    }
}