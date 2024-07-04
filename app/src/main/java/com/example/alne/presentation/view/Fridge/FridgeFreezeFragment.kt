package com.example.alne.view.Fridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.R
import com.example.alne.databinding.FragmentFridgeFreezeBinding
import com.example.alne.data.model.Food
import com.example.alne.data.model.Jwt
import com.example.alne.data.model.UserId
import com.example.alne.viewmodel.FridgeViewModel
import com.google.gson.Gson
import java.io.File

class FridgeFreezeFragment : Fragment(), MyCustomDialogDetailInterface {

    lateinit var binding: FragmentFridgeFreezeBinding
    lateinit var viewModel: FridgeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("FridgeFreezeFragment", "OnCreate")
        // Inflate the layout for this fragment
        binding = FragmentFridgeFreezeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(FridgeViewModel::class.java)

        val fridgeFrozenAdapter = FridgeFrozenAdapter(requireContext())
        binding.fridgeFrozenRv.adapter = fridgeFrozenAdapter
        binding.fridgeFrozenRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fridgeFrozenAdapter.setMyItemClickListener(object: FridgeFrozenAdapter.MyItemClickListener {
            override fun onItemClick(food: Food) {
                getCustomDialog(food)
            }

            // 보유재료 삭제 기능
            override fun onInfoClick(view: View, position: Int) {
                val popupBase = view.findViewById<ImageButton>(R.id.item_fridge_delete_ib)
                val popupMenu = PopupMenu(context, popupBase)
                popupMenu.menuInflater.inflate(R.menu.food_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { m ->
                    when(m.itemId){
                        R.id.menu_modify -> getCustomDialog(fridgeFrozenAdapter.items[position])
                        R.id.menu_delete -> {
                            fridgeFrozenAdapter.notifyDataSetChanged()
                            viewModel.deleteFridgeFood(
                                UserId(getUserToken().userId,
                                    fridgeFrozenAdapter.items[position].userId!!
                                )
                            )
                        }
                    }
                    false
                }
                popupMenu.show()
            }
        })


        viewModel.getFridgeLiveData.observe(viewLifecycleOwner, Observer { res ->
            Log.d("getFridge", res.toString())
            fridgeFrozenAdapter.addAllFood(res)
        })

        return binding.root
    }

    private fun getCustomDialog(food: Food){
        CustomDialogDetail(requireContext(),getUserToken(),food, this).show(requireActivity().supportFragmentManager, "CustomDialog")
    }

    fun getUserToken(): Jwt {
        val sharedPreferences = activity?.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        val userJwt = Gson().fromJson(sharedPreferences?.getString("jwt",null), Jwt::class.java)
        Log.d("getjwt", userJwt.toString())
        return userJwt
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("FridgeFreezeFragment", "onDestroy")
    }

    override fun onSubmitBtnDetailClicked(food: Food, photoFile: File?) {
        viewModel.addFridgeData(getUserToken().accessToken!!, food, photoFile)
    }

}