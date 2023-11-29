package com.example.alne.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.Network.FridgeApi
import com.example.alne.Network.FridgeGetResponse
import com.example.alne.R
import com.example.alne.databinding.FragmentFridgeAllBinding
import com.example.alne.model.Food
import com.example.alne.model.Fridge
import com.example.flo.Network.getRetrofit
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class FridgeAllFragment : Fragment() {
    lateinit var binding: FragmentFridgeAllBinding
    val items = arrayListOf<String>("냉장", "냉동")
    lateinit var fridgeadapter: FridgeAdapter
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FridgeAllFragment","onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("FridgeAllFragment","onCreateView")
        // Inflate the layout for this fragment
        binding = FragmentFridgeAllBinding.inflate(inflater, container, false)



        var testData = ArrayList<Food>().apply {
//            add(Food("사과",0,null,null,"2023-12-20까지"))
//            add(Food("바나나",0,null,null,"2023-11-20까지"))
//            add(Food("당근",0,null,null,"2023-12-230까지"))
//            add(Food("오이",0,null,null,"2023-11-26까지"))
//            add(Food("오이",0,null,null,"2023-11-26까지"))
//            add(Food("오이",0,null,null,"2023-11-26까지"))
//            add(Food("오이",0,null,null,"2023-11-26까지"))
//            add(Food("오이",0,null,null,"2023-11-26까지"))
        }
        fridgeadapter = FridgeAdapter()
        binding.fridgeAllRv.adapter = fridgeadapter
        binding.fridgeAllRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false )
        fridgeadapter.setMyItemClickListener(object: FridgeAdapter.MyItemClickListener{
            override fun onItemClick(food: Food) {
                getCustomDialog(food)
            }

            override fun onInfoClick(view: View, position: Int) {
                val popupBase = view.findViewById<ImageButton>(R.id.item_fridge_delete_ib)
                val popupMenu = PopupMenu(context, popupBase)
                popupMenu.menuInflater.inflate(R.menu.food_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { m ->
                    when(m.itemId){
                        R.id.menu_modify -> getCustomDialog(fridgeadapter.items[position])
                        R.id.menu_delete -> {
                            fridgeadapter.items.removeAt(position)
                            fridgeadapter.notifyDataSetChanged()
                        }
                    }
                    false
                }
                popupMenu.show()
            }
        })

//        binding.fridgeAllFloatingBt.setOnClickListener {
//            getCustomDialog(null)
//        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("FridgeAllFragment", "onStart")
        getFridgeFood()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FridgeAllFragment", "onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FridgeAllFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FridgeAllFragment", "onDestroy")
    }

    fun getFridgeFood(){
        retrofit = getRetrofit()
        retrofit.create(FridgeApi::class.java).getFridgeFood(getUserToken()).enqueue(object: Callback<FridgeGetResponse>{
            override fun onResponse(
                call: Call<FridgeGetResponse>,
                response: Response<FridgeGetResponse>,
            ) {
                Log.d("getFridgeFood", "onSuccess")
                if(response.isSuccessful) {
                    var res = response.body()
                    when(res?.status){
                        200 -> {
                            var fridge = res.data
                            var items: ArrayList<Food> = ArrayList()
                            for(item in fridge){
                                var name = item.ingredient.name
                                var image = item.ingredient.image
                                var storage = item.storage
                                var expire = item.exp
                                var memo = item.memo
                                var food = Food(null, name, expire, memo, storage, image)
                                items.add(food)
                            }
                            fridgeadapter.addAllFood(items)
                            Log.d("getFridgeFood", fridge[0].toString())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FridgeGetResponse>, t: Throwable) {
                Log.d("getFridgeFood", t.message.toString())
            }

        })
    }

    private fun getCustomDialog(food: Food?){
        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_foodadd,  null)
        val spinner = view.rootView.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = myAdapter
        spinner.setSelection(0)
        if(food != null){
            view.rootView.findViewById<TextInputEditText>(R.id.food_title_et).setText(food.name)
            view.rootView.findViewById<TextInputEditText>(R.id.food_memo_tv).setText(food.memo)

        }

        val alertDialog = AlertDialog.Builder(context, R.style.Dialog_food)
            .setView(view)
            .create()
        view.rootView.findViewById<AppCompatButton>(R.id.cancel_bt).setOnClickListener {
            alertDialog.dismiss()
        }
        view.rootView.findViewById<AppCompatButton>(R.id.submit_bt).setOnClickListener {
//            fridgeadapter.addFood(Food("사과",0,null,null,"2023-12-20까지"))
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun getUserToken(): Int{
        val sharedPreferences = activity?.getSharedPreferences("user_token", AppCompatActivity.MODE_PRIVATE)
        val userToken = sharedPreferences?.getInt("userId", 0)!!
        Log.d("getUserToken", userToken.toString())
        return userToken
    }



}