package com.example.alne.view.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.R
import com.example.alne.databinding.ItemExpireBinding
import com.example.alne.data.model.Food

class ExpireAdapter(var item: ArrayList<Food>): RecyclerView.Adapter<ExpireAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemExpireBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExpireBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.expireFoodIv.setImageResource(R.drawable.google)
        holder.binding.expireFoodTitleTv.text = item[position].name
//        holder.binding.expireDateInfoTv.text = item[position].expireDate
    }

}