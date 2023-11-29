package com.example.alne.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.R
import com.example.alne.databinding.ItemFridgeBinding
import com.example.alne.model.Food

class FridgeAdapter(): RecyclerView.Adapter<FridgeAdapter.ViewHolder>() {

    val items: ArrayList<Food> = ArrayList()

    interface MyItemClickListener {
        fun onItemClick(food: Food)
        fun onInfoClick(view: View, position: Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemFridgeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(food: Food){
            binding.itemFridgeTitleTv.text = food.name
            binding.itemFridgeExpireTv.text = food.exp
            binding.itemFridgeIv.setImageResource(R.drawable.bibimbap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeAdapter.ViewHolder {
        val binding = ItemFridgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FridgeAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.root.setOnClickListener{
            mItemClickListener.onItemClick(items[position])
        }

        holder.binding.itemFridgeDeleteIb.setOnClickListener{
            mItemClickListener.onInfoClick(it,position)
//            deleteFood(position)
        }
    }

    fun addAllFood(item: ArrayList<Food>){
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    fun deleteFood(position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size
}