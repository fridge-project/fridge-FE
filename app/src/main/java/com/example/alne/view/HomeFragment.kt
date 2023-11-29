package com.example.alne.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alne.R
import com.example.alne.databinding.FragmentHomeBinding
import com.example.alne.model.Food

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)


        var textItem: ArrayList<Food> = ArrayList()
//        textItem.add(Food("바나나",0,null,null,"2022.12.21"))
//        textItem.add(Food("오렌지",0,null,null,"2022.12.21"))
//        textItem.add(Food("사과",0,null,null,"2022.12.21"))
//        textItem.add(Food("옥수수",0,null,null,"2022.12.21"))
//        textItem.add(Food("옥수수",0,null,null,"2022.12.21"))
//        textItem.add(Food("옥수수",0,null,null,"2022.12.21"))
//        textItem.add(Food("옥수수",0,null,null,"2022.12.21"))
//        textItem.add(Food("옥수수",0,null,null,"2022.12.21"))



        var adapter = ExpireAdapter(textItem)
        binding.homeItemRv.adapter = adapter

        binding.homeSv.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { saveQuery(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return binding.root
    }

    fun saveQuery(item: String){
        val sharedPreferences = context?.getSharedPreferences("search_query", MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString("query",item)
        edit?.commit()
    }


}