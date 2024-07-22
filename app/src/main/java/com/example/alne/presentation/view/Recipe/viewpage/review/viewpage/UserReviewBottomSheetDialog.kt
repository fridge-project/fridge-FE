package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.alne.databinding.UserReviewDialogBinding
import com.example.alne.data.model.Comment
import com.example.alne.data.model.addComment
import com.example.alne.room.model.recipe
import com.example.alne.viewmodel.RecipeDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson

class UserReviewBottomSheetDialog(private val code: Int, private val position: Int): BottomSheetDialogFragment() {

    lateinit var binding: UserReviewDialogBinding
    lateinit var viewModel: RecipeDetailViewModel
    private var listener: OnSendFromBottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserReviewDialogBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(RecipeDetailViewModel::class.java)

        var recipe = Gson().fromJson(arguments?.getString("recipe"), recipe::class.java)
        var comment = Gson().fromJson(arguments?.getString("comment"), Comment::class.java)

        viewModel.userProfileLiveData.observe(viewLifecycleOwner, Observer { profile ->
            if(profile != null){
                Glide.with(requireContext()).load(profile.image).into(binding.reviewDialogUserProfileIv)
                binding.reviewDialogUserProfileIv.scaleType = ImageView.ScaleType.FIT_XY
                binding.itemReviewDialogIdTv.text = profile.name
            }
        })


        init(recipe, comment)


        binding.userReviewCancelBt.setOnClickListener {
            dismiss()
        }

        binding.userReviewSubmitBt.setOnClickListener {
            // 댓글 정보 서버로 보내기
            if(code == 0){
                viewModel.addUserComment(recipe._id, addComment(binding.userReviewCommentEt.text.toString(), binding.baseRatingBar.rating.toInt(), "url"))
            }else{
                viewModel.updateUserComment(position, addComment(binding.userReviewCommentEt.text.toString(), binding.baseRatingBar.rating.toInt(), "url"))
            }
            dismiss()
        }



        return binding.root
    }

    private fun init(recipe: recipe?, comment: Comment?){
        Glide.with(this).load(recipe?.imageURL).into(binding.userReviewMainFoodIv)
        if(comment != null) {
            binding.userReviewSubmitBt.text = "수정"
            binding.userReviewCommentEt.setText(comment.detail)
            binding.baseRatingBar.rating = comment.grade.toFloat()
            binding.itemReviewDialogIdTv.text = comment.user_id
        }
    }

    interface OnSendFromBottomSheetDialog {
        fun sendValue(value: String)
    }

    fun setCallback(listener: OnSendFromBottomSheetDialog) {
        this.listener = listener
    }
}