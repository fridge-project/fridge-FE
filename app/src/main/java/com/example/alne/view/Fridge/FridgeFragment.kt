package com.example.alne.view.Fridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.alne.databinding.FragmentFridgeBinding
import com.example.alne.data.model.Food
import com.example.alne.data.model.Jwt
import com.example.alne.viewmodel.FridgeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.io.File


class FridgeFragment : Fragment(), MyCustomDialogInterface{

    lateinit var binding: FragmentFridgeBinding
    private val information = arrayListOf("All", "냉장","냉동")
    lateinit var viewModel : FridgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFridgeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(FridgeViewModel::class.java)

        //ViewPager - TabLayout 연결
        val fridgeAdapter = FridgeVPAdapter(this)
        binding.fridgeVp.adapter = fridgeAdapter
        TabLayoutMediator(binding.fridgeTl, binding.fridgeVp){ tab, position ->
            tab.text = information[position]
        }.attach()

        // 재료 추가 버튼 클릭 시
        binding.fridgeFloatingBt.setOnClickListener{
            CustomDialogAdd(requireContext(), getUserToken(), this@FridgeFragment).show(requireActivity().supportFragmentManager, "CustomDialog")
        }

        return binding.root
    }

    //Jwt 가져오기 수정
    fun getUserToken(): Jwt {
        val sharedPreferences = activity?.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        val userJwt = Gson().fromJson(sharedPreferences?.getString("jwt",null), Jwt::class.java)
        return userJwt
    }

    override fun onSubmitBtnClicked(food: Food, photoFile: File?) {
        viewModel.addFridgeData(getUserToken().accessToken!!, food, photoFile)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FridgeFragment", "destroy")
    }
}