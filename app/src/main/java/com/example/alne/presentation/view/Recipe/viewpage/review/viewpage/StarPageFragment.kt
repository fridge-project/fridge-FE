package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alne.databinding.FragmentStarPageBinding
import com.example.alne.viewmodel.RecipeDetailViewModel

class StarPageFragment : Fragment() {

    lateinit var binding: FragmentStarPageBinding
    lateinit var viewModel: RecipeDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("starPageFragment", "onCreateView")
        binding = FragmentStarPageBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(RecipeDetailViewModel::class.java)
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
        with(binding){
            expireProgressPb.max = size
            expireProgressPb.progress = grade[1] / size
            binding.expireProgressCountTv.text = grade[1].toString()

            expireProgressPb1.max = size
            expireProgressPb1.progress = grade[2] / size
            binding.expireProgressCountTv1.text = grade[2].toString()

            expireProgressPb2.max = size
            expireProgressPb2.progress = grade[3] / size
            binding.expireProgressCountTv2.text = grade[3].toString()

            expireProgressPb3.max = size
            expireProgressPb3.progress = grade[4] / size
            binding.expireProgressCountTv3.text = grade[4].toString()

            expireProgressPb4.max = size
            expireProgressPb4.progress = grade[5] / size
            binding.expireProgressCountTv4.text = grade[5].toString()
        }
    }
}