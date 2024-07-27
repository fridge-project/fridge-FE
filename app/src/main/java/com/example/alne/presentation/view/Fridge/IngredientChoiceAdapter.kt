package com.example.alne.view.Fridge

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.data.model.RoomIngredient
import com.example.alne.databinding.ItemChoiceIngredientBinding

class IngredientChoiceAdapter(): RecyclerView.Adapter<IngredientChoiceAdapter.ViewHolder>(), Filterable{

    var items: ArrayList<RoomIngredient> = ArrayList()
    var itemsFilter: ArrayList<RoomIngredient> = items
    inner class ViewHolder(val binding: ItemChoiceIngredientBinding): RecyclerView.ViewHolder(binding.root){}

    interface MyItemClickListener {
        fun onItemClick(name: String)
    }
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): IngredientChoiceAdapter.ViewHolder {
        val binding = ItemChoiceIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemChoiceIngredientTv.text = items[position].name
        holder.binding.root.setOnClickListener {
            mItemClickListener.onItemClick(items[position].name)
        }
    }

    override fun getItemCount(): Int = items.size
    override fun getFilter(): Filter {
        var filter = object: Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var filterResults: FilterResults = FilterResults()
                if(p0?.isEmpty()!! || p0 == null){
                    filterResults.count = itemsFilter.size
                    filterResults.values = itemsFilter
                }else{
                    var searchResult: ArrayList<RoomIngredient> = ArrayList()
                    for(food in itemsFilter){
                        if(food.name.contains(p0)){
                            searchResult.add(food)
                        }
                    }
                    filterResults.count = searchResult.size
                    filterResults.values = searchResult
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                items = p1?.values as ArrayList<RoomIngredient>
                notifyDataSetChanged()
            }
        }
        return filter
    }

    fun addAllItems(items: ArrayList<RoomIngredient>){
        this.items.clear()
        this.items.addAll(items)
        Log.d("items", items.toString())
        notifyDataSetChanged()
    }


}