package com.example.alne.view.Recipe.viewpage.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.databinding.ItemOrderBinding
import com.example.alne.data.model.RecipeProcess

class ReviewRVAdapter(val items: ArrayList<RecipeProcess>): RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemOrderTitle.text = "Step " + String.format("%02d", items[position].order_num)
        holder.binding.itemOrderTv.text = items[position].detail
    }

}