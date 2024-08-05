package com.example.alne.presentation.view.Recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.databinding.ItemCategoryBinding
import java.util.ArrayList

class CategoryRVAdapter(var item: ArrayList<String>): RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: String){
            binding.itemCategoryTv.text = "#$category"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryRVAdapter.ViewHolder {
        var binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int = item.size
}