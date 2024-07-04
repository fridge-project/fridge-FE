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
import com.example.alne.data.model.Grade
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
        viewModel.getRecipeProcessLiveData.observe(viewLifecycleOwner, Observer{recipeProcess ->
            var grade: Grade = recipeProcess.gradeDto
            binding.startPageRatingbar.rating = grade.average.toFloat()
            binding.starPageAverageStar.text = grade.average.toString()
            setRatingProgress(grade, recipeProcess.comments.size)
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setRatingProgress(grade: Grade, size: Int){
        with(binding){
            expireProgressPb.max = size
            expireProgressPb.progress = grade.count.get(0).toInt() / size
            binding.expireProgressCountTv.text = grade.count.get(0).toInt().toString()

            expireProgressPb1.max = size
            expireProgressPb1.progress = grade.count.get(1).toInt() / size
            binding.expireProgressCountTv1.text = grade.count.get(1).toInt().toString()

            expireProgressPb2.max = size
            expireProgressPb2.progress = grade.count.get(2).toInt() / size
            binding.expireProgressCountTv2.text = grade.count.get(2).toInt().toString()

            expireProgressPb3.max = size
            expireProgressPb3.progress = grade.count.get(3).toInt() / size
            binding.expireProgressCountTv3.text = grade.count.get(3).toInt().toString()

            expireProgressPb4.max = size
            expireProgressPb4.progress = grade.count.get(4).toInt() / size
            binding.expireProgressCountTv4.text = grade.count.get(4).toInt().toString()



        }
    }
}