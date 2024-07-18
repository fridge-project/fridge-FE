package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.data.model.Comment
import com.example.alne.databinding.FragmentReviewPageBinding
import com.example.alne.room.model.recipe
import com.example.alne.viewmodel.RecipeDetailViewModel
import com.google.gson.Gson

class ReviewPageFragment(val recipe: recipe) : Fragment() {

    lateinit var binding: FragmentReviewPageBinding
    lateinit var viewModel: RecipeDetailViewModel
    lateinit var adapter: ReviewPageRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewPageBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(RecipeDetailViewModel::class.java)
        Log.d("ReviewPageFragment", "onCreateView")

        initAdapter()

        viewModel.getRecipeProcessLiveData.observe(viewLifecycleOwner, Observer{
            //adapter.addAllItem(it.comments as ArrayList<Comments>)
        })

        viewModel.usersCommentsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addAllItem(it)
        })


        binding.reviewPageReviewBt.setOnClickListener {
            var bundle: Bundle = Bundle()
            bundle.putString("recipe", Gson().toJson(recipe))
            startUserReviewBottomSheetDialog(bundle)
        }

        return binding.root
    }

    private fun initAdapter() {
        adapter = ReviewPageRVAdapter(requireContext())
        adapter.setMyItemClickListener(object: ReviewPageRVAdapter.MyItemClickListener{
            override fun deleteComment(position: Int) {
                viewModel.deleteUserComment(position)
            }
            override fun patchComment(comment: Comment) {
                var bundle: Bundle = Bundle()
                bundle.putString("recipe", Gson().toJson(recipe))
                bundle.putString("comment", Gson().toJson(comment))
                startUserReviewBottomSheetDialog(bundle)
            }

            override fun initUi(bool: Boolean) {
                setUi(bool)
            }
        })
        binding.reviewPageRv.adapter = adapter
        binding.reviewPageRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUi(bool: Boolean){
        if(bool){
            binding.reviewPageReviewBt.visibility = View.GONE
        }else{
            binding.reviewPageReviewBt.visibility = View.VISIBLE
        }
    }

    private fun startUserReviewBottomSheetDialog(bundle: Bundle){
        val dialog = UserReviewBottomSheetDialog()
        dialog.arguments = bundle
        dialog.show(childFragmentManager, "")
        Log.d("reviewPageReviewBt", bundle.toString())
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}