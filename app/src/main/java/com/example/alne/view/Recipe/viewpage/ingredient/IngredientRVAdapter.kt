package com.example.alne.view.Recipe.viewpage.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.databinding.ItemIngredientBinding
import com.example.alne.data.model.Ingredient

class IngredientRVAdapter(val items: ArrayList<Ingredient>): RecyclerView.Adapter<IngredientRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ingredient: Ingredient){
            binding.itemIngredientTitleTv.text = ingredient.name
            binding.itemIngredientDetailTv.text = ingredient.amount
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}