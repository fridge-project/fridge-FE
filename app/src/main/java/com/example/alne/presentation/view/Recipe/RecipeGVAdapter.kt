package com.example.alne.view.Recipe

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alne.R
import com.example.alne.data.model.Recipe
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
            binding.itemRecipeTitleTv.text = recipe.name
            binding.itemRecipeTimeTv.text = "약 " + recipe.time + "분"
            binding.itemRecipeRankTv.text = recipe.difficulty
            Glide.with(context).load(recipe.imageurl).into(binding.itemRecipeIv)
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

//
//    fun addItems(item: ArrayList<recipe>){
//        items.clear()
//        items.addAll(item)
//        itemsFilter.addAll(item)
//        notifyDataSetChanged()
//        Log.d("items", items.toString())
//    }

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
                        if(recipe.name.contains(p0)){
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

//class RecipeGVAdapter(val context: Context): BaseAdapter(), Filterable, PagingDataAdapter<recipe, > {
//
//    var items: ArrayList<recipe> = ArrayList()
//    var itemsFilter: ArrayList<recipe> = ArrayList()
//
//
//    interface setOnClickListener {
//        fun clickItem(recipe: recipe)
//    }
//
//    private lateinit var myItemClickListener: setOnClickListener
//
//    fun setMyItemClickListener(itemClickListener: setOnClickListener){
//        myItemClickListener = itemClickListener
//    }
//
//    override fun getCount(): Int = items.size
//
//    override fun getItem(p0: Int): Any {
//        return items[p0]
//    }
//
//    override fun getItemId(p0: Int): Long {
//        return p0.toLong()
//    }
//
//    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
//        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recipe, null)
//        view.findViewById<TextView>(R.id.item_recipe_title_tv).text = items[p0].name
//        view.findViewById<TextView>(R.id.item_recipe_time_tv).text = "약 " + items[p0].time + "분"
//        view.findViewById<TextView>(R.id.item_recipe_rank_tv).text = items[p0].difficulty
//        Glide.with(context).load(items[p0].imageurl).into(view.findViewById<ImageView>(R.id.item_recipe_iv))
//        view.setOnClickListener{
//            myItemClickListener.clickItem(items[p0])
//        }
////        binding.itemRecipeIv.setImageResource(items[p0].imageURL)
//        return view
//    }
//    fun addItems(item: ArrayList<recipe>){
//        items.clear()
//        items.addAll(item)
//        itemsFilter.addAll(item)
//        notifyDataSetChanged()
//        Log.d("items", items.toString())
//    }
//
//    override fun getFilter(): Filter {
//        var filter  = object: Filter(){
//            override fun performFiltering(p0: CharSequence?): FilterResults {
//                var filterResults = FilterResults()
//                if(p0?.isEmpty()!! || p0 == null){
//                    filterResults.count = itemsFilter.size
//                    filterResults.values = itemsFilter
//                }else{
//                    var searchResult: ArrayList<recipe> = ArrayList()
//                    for(recipe in itemsFilter){
//                        if(recipe.name.contains(p0)){
//                            searchResult.add(recipe)
//                        }
//                    }
//                    filterResults.count = searchResult.size
//                    filterResults.values = searchResult
//                }
//                return filterResults
//            }
//
//            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//                items = p1?.values as ArrayList<recipe>
//                notifyDataSetChanged()
//            }
//        }
//        return filter
//    }
//}