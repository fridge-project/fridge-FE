package com.example.alne.view.Recipe.viewpage.review.viewpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alne.GlobalApplication
import com.example.alne.databinding.ItemReviewBinding
import com.example.alne.data.model.Comments
import com.example.alne.data.model.Review

class ReviewPageRVAdapter(val context: Context): RecyclerView.Adapter<ReviewPageRVAdapter.ViewHolder>() {

    private var items: ArrayList<Comments> = ArrayList()
    interface MyItemClickListener {
        fun deleteComment(position: Int)
        fun patchComment(comment: Comments)

        fun initUi(bool: Boolean)
    }

    fun addAllItem(items: ArrayList<Comments>){
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
        fun bind(comment: Comments){
            binding.itemReviewNickname.text = comment.user.name
            binding.itemReviewSummary.text = comment.detail
//            binding.itemReviewDate.text = review.date
            binding.itemReviewRatingbar.rating = comment.grade.toFloat()
            binding.itemReviewRatingTv.text = comment.grade.toString()
            Glide.with(context).load(comment.user.image).into(binding.itemReviewIv)
            binding.itemReviewIv.scaleType = ImageView.ScaleType.FIT_XY
            binding.itemReviewIv.setPadding(0,0,0,0)
//            if(comment.user.id == GlobalApplication.prefManager.getUserToken()?.userId){
//                binding.itemReviewInfo.visibility = View.VISIBLE
//                binding.itemReviewRecomment.setOnClickListener {
//                    myItemClickListener.patchComment(comment)
//                }
//                myItemClickListener.initUi(true)
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
    }


}