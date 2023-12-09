package com.example.alne.view.Recipe.viewpage.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.databinding.ItemOrderBinding

class ReviewRVAdapter(val items: ArrayList<String>): RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemOrderTitle.text = "Step 0"+(position+1)
        holder.binding.itemOrderTv.text = items[position]
    }

}