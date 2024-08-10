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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.R
import com.example.alne.databinding.FragmentFridgeColdBinding
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.data.model.Jwt
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.viewmodel.FridgeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FridgeColdFragment : Fragment(), MyCustomDialogDetailInterface {
    lateinit var binding: FragmentFridgeColdBinding
    private val viewModel: FridgeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("FridgeColdFragment", "onCreateView")
        // Inflate the layout for this fragment
        binding = FragmentFridgeColdBinding.inflate(inflater, container, false)

        val fridgeColdAdapter = FridgeColdAdapter(requireContext())
        binding.fridgeColdRv.adapter = fridgeColdAdapter
        binding.fridgeColdRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fridgeColdAdapter.setMyItemClickListener(object: FridgeColdAdapter.MyItemClickListener {
            override fun onItemClick(food: FridgeIngredient, position: Int) {
                getCustomDialog(food, position)
            }

            // 보유재료 삭제 기능
            override fun onInfoClick(view: View, position: Int) {
                val popupBase = view.findViewById<ImageButton>(R.id.item_fridge_delete_ib)
                val popupMenu = PopupMenu(context, popupBase)
                popupMenu.menuInflater.inflate(R.menu.food_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { m ->
                    when(m.itemId){
                        R.id.menu_modify -> getCustomDialog(fridgeColdAdapter.items[position], position)
                        R.id.menu_delete -> {
                            fridgeColdAdapter.notifyDataSetChanged()
//                            viewModel.deleteFridgeFood(
//                                UserId(getUserToken().userId,
//                                    fridgeColdAdapter.items[position].userId!!
//                                )
//                            )
                        }
                    }
                    false
                }
                popupMenu.show()
            }
        })


        viewModel.getFridgeLiveData.observe(viewLifecycleOwner, Observer { res ->
            Log.d("getFridge", res.toString())
            fridgeColdAdapter.addAllFood(res)
        })



        return binding.root
    }

    private fun getCustomDialog(food: FridgeIngredient, position: Int){
        CustomDialogDetail(requireContext(),food, position, this).show(requireActivity().supportFragmentManager, "CustomDialog")
    }

    fun getUserToken(): Jwt {
        val sharedPreferences = activity?.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        val userJwt = Gson().fromJson(sharedPreferences?.getString("jwt",null), Jwt::class.java)
        Log.d("getjwt", userJwt.toString())
        return userJwt
    }
//    override fun onSubmitBtnDetailClicked(food: FridgeIngredient, photoFile: File?) {
//        viewModel.addFridgeData(getUserToken().accessToken!!, food, photoFile)
//    }

    override fun onSubmitBtnDetailClicked(food: FridgeIngredient, position: Int) {
        Log.d("onSubmitBtnDetailClicked", "launch")
        viewModel.updateFridgeFood(food, position)
    }
}