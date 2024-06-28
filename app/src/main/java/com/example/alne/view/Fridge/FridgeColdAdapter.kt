package com.example.alne.view.Fridge

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.icu.util.Calendar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alne.R
import com.example.alne.databinding.ItemFridgeBinding
import com.example.alne.data.model.Food
import java.text.SimpleDateFormat

class FridgeColdAdapter(val context: Context): RecyclerView.Adapter<FridgeColdAdapter.ViewHolder>() {

    val items: ArrayList<Food> = ArrayList()
    var calendar = Calendar.getInstance()
    var sfp = SimpleDateFormat("yyyy.MM.dd")
    init {
        Log.d("adapter", "2")
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND,0)
        calendar.set(Calendar.MILLISECONDS_IN_DAY,0)
    }

    interface MyItemClickListener {
        fun onItemClick(food: Food)
        fun onInfoClick(view: View, position: Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemFridgeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {

            // 마감 날짜
            var expDate = sfp.parse(food.exp!!.split(" ")[0])

            // 등록 날짜
            var addDate = sfp.parse(food.addDate!!.split(" ")[0])

            // 유효날짜 - 등록 날짜
            var dateLength = (expDate.time - addDate.time)/ (60 * 60 * 24 * 1000)

            // 유효기간까지 남음 시간
            var needDiff = ((expDate.time - (calendar.time.time+1)))/ (60 * 60 * 24 * 1000)

            // 날짜 차이 (지난 시간)
            var diff: Int = (dateLength - needDiff).toInt()

            binding.itemFridgeTitleTv.text = food.name
            binding.itemFridgeExpireTv.text = food.exp!!.split(" ")[0] + " 까지"
            if(needDiff < 0){
                binding.itemFridgeExpireInfoTv.text = "유효기간 ${-needDiff}일 지남"
            }else{
                binding.itemFridgeExpireInfoTv.text = "유효기간 ${needDiff}일 남음"
            }

            if(food.imageUrl != null){
                Glide.with(context).load(food.imageUrl).into(binding.itemFridgeIv)
            }else{
                binding.itemFridgeIv.setImageResource(R.drawable.camera )
            }


            if(food.storage == "FROZEN"){
                binding.itemFridgeStorageTv.text = "냉동"
            }else{
                binding.itemFridgeStorageTv.text = "냉장"
            }

            if(needDiff > 15){
                binding.itemFridgeExpireInfoTv.setTextColor(Color.parseColor("#00FF1A"))
                binding.itemFridgePb.progressDrawable = ContextCompat.getDrawable(context, R.drawable.progressbar_border_low)
            }else if(needDiff in 8..14){
                binding.itemFridgeExpireInfoTv.setTextColor(Color.parseColor("#FFD500"))
                binding.itemFridgePb.progressDrawable = ContextCompat.getDrawable(context, R.drawable.progressbar_border_low_high)
            }
            else if(needDiff in 4..7){
                binding.itemFridgeExpireInfoTv.setTextColor(Color.parseColor("#FF9900"))
                binding.itemFridgePb.progressDrawable = ContextCompat.getDrawable(context, R.drawable.progressbar_border_high_low)
            }else{
                binding.itemFridgeExpireInfoTv.setTextColor(Color.RED)
                binding.itemFridgePb.progressDrawable = ContextCompat.getDrawable(context, R.drawable.progressbar_border_high)
            }

            if(dateLength <= 0){
                binding.itemFridgePb.max = 100
                binding.itemFridgePb.progress = 100
            }else{
                if(diff == 0){
                    binding.itemFridgePb.progress = 1
                }else{
                    var mul: Int = (100 / dateLength).toInt()
                    binding.itemFridgePb.progress = mul*diff
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFridgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.root.setOnClickListener{
            mItemClickListener.onItemClick(items[position])
        }

        holder.binding.itemFridgeDeleteIb.setOnClickListener{
            mItemClickListener.onInfoClick(it,position)
        }
    }

    fun addAllFood(item: ArrayList<Food>){
        items.clear()
        for(food in item){
            if(food.storage == "COLD"){
                items.add(food)
            }
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size
}