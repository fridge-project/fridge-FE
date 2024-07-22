package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alne.GlobalApplication
import com.example.alne.data.model.Comment
import com.example.alne.databinding.ItemReviewBinding
import com.example.alne.data.model.Review

class ReviewPageRVAdapter(val context: Context): RecyclerView.Adapter<ReviewPageRVAdapter.ViewHolder>() {

    private var items: ArrayList<Comment> = ArrayList()
    interface MyItemClickListener {
        fun deleteComment(position: Int)
        fun patchComment(comment: Comment, position: Int)

        fun initUi(bool: Boolean)
    }

    fun addAllItem(items: ArrayList<Comment>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(myItemClickListener: MyItemClickListener){
        this.myItemClickListener = myItemClickListener
    }

    inner class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment){
            binding.itemReviewNickname.text = comment.user_id
            binding.itemReviewSummary.text = comment.detail
//            binding.itemReviewDate.text = review.date
            binding.itemReviewRatingbar.rating = comment.grade.toFloat()
            binding.itemReviewRatingTv.text = comment.grade.toString()

            // 사용자 이미지
            //Glide.with(context).load(comment.).into(binding.itemReviewIv)
            binding.itemReviewIv.scaleType = ImageView.ScaleType.FIT_XY
            binding.itemReviewIv.setPadding(0,0,0,0)
//            if(comment.user.id == GlobalApplication.prefManager.getUserToken()?.userId){
                binding.itemReviewInfo.visibility = View.VISIBLE
                myItemClickListener.initUi(true)
//            }else{
//                binding.itemReviewInfo.visibility = View.INVISIBLE
//                myItemClickListener.initUi(false)
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.itemReviewDelete.setOnClickListener {
            myItemClickListener.deleteComment(position)
        }

        holder.binding.itemReviewRecomment.setOnClickListener {
            myItemClickListener.patchComment(items[position], position)
        }
    }


}