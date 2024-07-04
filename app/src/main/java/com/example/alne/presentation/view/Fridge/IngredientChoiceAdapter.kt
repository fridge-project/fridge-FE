package com.example.alne.view.Fridge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.alne.databinding.ItemChoiceIngredientBinding
import com.example.alne.room.model.food
import com.example.alne.room.model.recipe

class IngredientChoiceAdapter(var items: ArrayList<food>): RecyclerView.Adapter<IngredientChoiceAdapter.ViewHolder>(), Filterable{

    var itemsFilter: ArrayList<food> = items
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
                    var searchResult: ArrayList<food> = ArrayList()
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
                items = p1?.values as ArrayList<food>
                notifyDataSetChanged()
            }
        }
        return filter
    }


}