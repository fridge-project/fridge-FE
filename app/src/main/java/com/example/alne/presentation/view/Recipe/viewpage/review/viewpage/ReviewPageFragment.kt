package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.data.model.Comment
import com.example.alne.databinding.FragmentReviewPageBinding
import com.example.alne.domain.model.recipe
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.viewmodel.RecipeDetailViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewPageFragment(val recipe: recipe) : Fragment() {

    lateinit var binding: FragmentReviewPageBinding
    private val viewModel: RecipeDetailViewModel by activityViewModels()
    lateinit var adapter: ReviewPageRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewPageBinding.inflate(layoutInflater)
        Log.d("ReviewPageFragment", "onCreateView")

        initAdapter()

        viewModel.usersCommentsLiveData.observe(viewLifecycleOwner, Observer { it ->
            Log.d("usersCommentsLiveData", it.toString())
            adapter.addAllItem(it)
            it.stream().forEach { comment ->
                if(comment.username == viewModel.nickname){
                    setUi(true)
                }else{
                    setUi(false)
                }
            }
        })

        binding.reviewPageReviewBt.setOnClickListener {
            var bundle: Bundle = Bundle()
            bundle.putString("recipe", Gson().toJson(recipe))
            startUserReviewBottomSheetDialog(0,bundle, 0)
        }

        return binding.root
    }

    private fun initAdapter() {
        Log.d("nicknameadapter", viewModel.nickname)
        adapter = ReviewPageRVAdapter(requireContext(), viewModel.nickname)
        adapter.setMyItemClickListener(object: ReviewPageRVAdapter.MyItemClickListener{
            override fun deleteComment(position: Int) {
                viewModel.deleteUserComment(position, completion = { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY -> initUi(false)
                        RESPONSE_STATUS.FAIL, RESPONSE_STATUS.NETWORK_ERROR -> {
                            Toast.makeText(requireContext(), "서버 오류입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }
            override fun patchComment(comment: Comment, position: Int) {
                var bundle: Bundle = Bundle()
                bundle.putString("recipe", Gson().toJson(recipe))
                bundle.putString("comment", Gson().toJson(comment))
                startUserReviewBottomSheetDialog(1, bundle, position)
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
            binding.reviewPageReviewBt.visibility = View.INVISIBLE
        }else{
            binding.reviewPageReviewBt.visibility = View.VISIBLE
        }
    }

    private fun startUserReviewBottomSheetDialog(code: Int, bundle: Bundle, position: Int){
        val dialog = UserReviewBottomSheetDialog(code, position)
        dialog.arguments = bundle
        dialog.show(childFragmentManager, "")
        Log.d("reviewPageReviewBt", bundle.toString())
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}