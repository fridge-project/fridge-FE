package com.example.alne.view.Recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alne.databinding.ItemRecipeBinding
import com.example.alne.room.model.recipe

class RecipeGVAdapter(val context: Context): Filterable, PagingDataAdapter<recipe, RecipeGVAdapter.ViewHolder>(ARTICLE_DIFF_CALLBACK) {

    var items: ArrayList<recipe> = ArrayList()
    var itemsFilter: ArrayList<recipe> = ArrayList()


    interface setOnClickListener {
        fun clickItem(recipe: recipe)
    }

    private lateinit var myItemClickListener: setOnClickListener

    fun setMyItemClickListener(itemClickListener: setOnClickListener){
        myItemClickListener = itemClickListener
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<recipe>() {
            override fun areItemsTheSame(oldItem: recipe, newItem: recipe): Boolean =
                oldItem.recipe_code == newItem.recipe_code

            override fun areContentsTheSame(oldItem: recipe, newItem: recipe): Boolean =
                oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: recipe) {
            binding.itemRecipeTitleTv.text = recipe.recipe
            binding.itemRecipeTimeTv.text = "약 " + recipe.time
            binding.itemRecipeRankTv.text = recipe.difficulty
            Glide.with(context).load(recipe.imageURL).into(binding.itemRecipeIv)
            binding.root.setOnClickListener {
                myItemClickListener.clickItem(recipe)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getFilter(): Filter {
        var filter  = object: Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var filterResults = FilterResults()
                if(p0?.isEmpty()!! || p0 == null){
                    filterResults.count = itemsFilter.size
                    filterResults.values = itemsFilter
                }else{
                    var searchResult: ArrayList<recipe> = ArrayList()
                    for(recipe in itemsFilter){
                        if(recipe.recipe.contains(p0)){
                            searchResult.add(recipe)
                        }
                    }
                    filterResults.count = searchResult.size
                    filterResults.values = searchResult
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                items = p1?.values as ArrayList<recipe>

                notifyDataSetChanged()
            }
        }
        return filter
    }
}