package com.example.alne.view.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alne.databinding.ItemRecipeRankBinding
import com.example.alne.room.model.recipe


class HomeRecipeRankRVAdapter(var context: Context): RecyclerView.Adapter<HomeRecipeRankRVAdapter.ViewHolder>() {

    var items: ArrayList<recipe> = ArrayList()

    interface setOnClickListener {
        fun clickItem(recipe: recipe)
    }

    private lateinit var myItemClickListener: setOnClickListener

    fun setMyItemClickListener(itemClickListener: setOnClickListener){
        myItemClickListener = itemClickListener
    }


    inner class ViewHolder(var binding: ItemRecipeRankBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecipeRankRVAdapter.ViewHolder {
        var binding = ItemRecipeRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemRecipeTitleTv.text = items[position].name
        holder.binding.itemRecipeTimeTv.text = "약 " + items[position].time + "분"
        holder.binding.itemRecipeRankTv.text = items[position].difficulty
        Glide.with(context).load(items[position].imageurl).into(holder.binding.itemRecipeIv)
        holder.binding.root.setOnClickListener{
            myItemClickListener.clickItem(items[position])
        }
    }

    fun addItems(item: ArrayList<recipe>){
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

}