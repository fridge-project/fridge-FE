package com.example.alne.view

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.alne.Network.FridgeApi
import com.example.alne.Network.FridgePostResponse
import com.example.alne.databinding.FragmentFridgeBinding
import com.example.alne.model.Food
import com.example.flo.Network.getRetrofit
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FridgeFragment : Fragment() {

    lateinit var binding: FragmentFridgeBinding
    private val information = arrayListOf("All", "냉장","냉동")
    val items = arrayListOf<String>("냉장", "냉동")
    lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("FridgeFragment", "onCreateView()")
        // Inflate the layout for this fragment
        binding = FragmentFridgeBinding.inflate(inflater, container, false)


        val fridgeAdapter = FridgeVPAdapter(this)
        binding.fridgeVp.adapter = fridgeAdapter
        TabLayoutMediator(binding.fridgeTl, binding.fridgeVp){ tab, position ->
            tab.text = information[position]
        }.attach()


        binding.fridgeFloatingBt.setOnClickListener{
            getCustomDialog(null)
        }
        return binding.root
    }

    private fun getCustomDialog(food: Food?){
        val myAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, items)
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(com.example.alne.R.layout.item_foodadd,  null)
        val spinner = view.rootView.findViewById<Spinner>(com.example.alne.R.id.spinner)
        spinner.adapter = myAdapter
        spinner.setSelection(0)
        var storage: String? = null
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               when(p2){
                   0 -> {
                       storage = "COLD"
                   }
                   1-> {
                       storage = "FROZEN"
                   }
               }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        if(food != null){
            view.rootView.findViewById<TextInputEditText>(com.example.alne.R.id.food_title_et).setText(food.name)
            view.rootView.findViewById<TextInputEditText>(com.example.alne.R.id.food_memo_tv).setText(food.memo)

        }

        val alertDialog = AlertDialog.Builder(context, com.example.alne.R.style.Dialog_food)
            .setView(view)
            .create()
        view.rootView.findViewById<AppCompatButton>(com.example.alne.R.id.cancel_bt).setOnClickListener {
            alertDialog.dismiss()
        }
        view.rootView.findViewById<AppCompatButton>(com.example.alne.R.id.submit_bt).setOnClickListener {
//            fridgeadapter.addFood(Food("사과",0,null,null,"2023-12-20까지"))
            val title = view.rootView.findViewById<TextInputEditText>(com.example.alne.R.id.food_title_et).text.toString()
//            val memo = view.rootView.findViewById<TextInputEditText>(com.example.alne.R.id.food_memo_tv).text.toString()
//            val exp = "000000"
            Log.d("data", title + " "+ storage)
            addFridgeData(title,null,null,storage!!)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun addFridgeData(title: String, exp: String?, memo: String?, storage: String){
        retrofit = getRetrofit()
        retrofit.create(FridgeApi::class.java).addFridgeFood(Food(getUserToken(), title,exp,memo,storage, null)).enqueue(object: Callback<FridgePostResponse>{
            override fun onResponse(
                call: Call<FridgePostResponse>,
                response: Response<FridgePostResponse>,
            ) {
                Log.d("addFridgeData", "onSuccess")
                Log.d("addFridgeData", response.body().toString())
                if(response.isSuccessful) {
                    var res = response.body()
                    when(res?.status){
                        200 -> {
                            Log.d("addFridgeData", "재료 등록 성공")
                            Log.d("addFridgeData", response.body()?.data.toString())
                        }
                        401 -> {
                            Log.d("addFridgeData", "재료 등록 실패")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FridgePostResponse>, t: Throwable) {
                Log.d("addFridgeData", "onFailure")
            }

        })
    }




    override fun onStart() {
        Log.d("FridgeFragment", "onStart()")
        super.onStart()
    }
    override fun onPause() {
        Log.d("FridgeFragment", "onPause()")
        super.onPause()
    }

    override fun onDestroyView() {
        Log.d("FridgeFragment", "onDestroyView()")
        super.onDestroyView()
    }

//    override fun onItemClick(food: Food) {
//        getCustomDialog(food)
//    }

    fun getUserToken(): Int{
        val sharedPreferences = activity?.getSharedPreferences("user_token", AppCompatActivity.MODE_PRIVATE)
        val userToken = sharedPreferences?.getInt("userId", 0)!!
        Log.d("getUserToken", userToken.toString())
       return userToken
    }
}