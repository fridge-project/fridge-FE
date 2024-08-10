package com.example.alne.view.MyPage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.alne.R
import com.example.alne.data.model.RecipeModel

class RecipeGVAdapter(val context: Context): BaseAdapter() {

    var items: ArrayList<RecipeModel> = ArrayList()

    interface setOnClickListener {
        fun clickItem(recipe: RecipeModel)
    }

    private lateinit var myItemClickListener: setOnClickListener

    fun setMyItemClickListener(itemClickListener: setOnClickListener){
        myItemClickListener = itemClickListener
    }

    override fun getCount(): Int = items.size

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recipe, null)
        view.findViewById<TextView>(R.id.item_recipe_title_tv).text = items[p0].recipe
        view.findViewById<TextView>(R.id.item_recipe_time_tv).text = "약 " + items[p0].time + "분"
        view.findViewById<TextView>(R.id.item_recipe_rank_tv).text = items[p0].difficulty
        Glide.with(context).load(items[p0].imageURL).into(view.findViewById<ImageView>(R.id.item_recipe_iv))
        view.setOnClickListener{
            myItemClickListener.clickItem(items[p0])
        }
//        binding.itemRecipeIv.setImageResource(items[p0].imageURL)
        return view
    }
    fun addItems(item: ArrayList<RecipeModel>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
        Log.d("items", items.toString())
    }
}